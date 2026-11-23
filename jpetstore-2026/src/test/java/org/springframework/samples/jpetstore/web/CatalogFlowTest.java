package org.springframework.samples.jpetstore.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.jpetstore.TestApplication;

public class CatalogFlowTest {

	private ShopClient client;

	@Before
	public void setUp() throws Exception {
		TestApplication.reset();
		client = new ShopClient();
	}

	@Test
	public void showsStoreIndex() throws Exception {
		assertEquals("index", client.get("/shop/index.do").view());
	}

	@Test
	public void showsCategoryWithFirstPageOfProducts() throws Exception {
		Page page = client.get("/shop/viewCategory.do", "categoryId", "FISH");
		assertEquals("Category", page.view());
		assertEquals("Fish", page.value("category.name"));
		assertEquals(4, page.value("productList.nrOfElements"));
		assertEquals(4, page.value("productList.pageSize"));
	}

	@Test
	public void pagesThroughProductsOfCategory() throws Exception {
		client.get("/shop/viewCategory.do", "categoryId", "DOGS");
		Page next = client.get("/shop/viewCategory.do", "page", "next");
		assertEquals("Category", next.view());
		assertEquals("Dogs", next.value("category.name"));
		assertEquals(1, next.value("productList.page"));
		assertEquals(2, ((java.util.List<?>) next.value("productList.pageList")).size());
		Page previous = client.get("/shop/viewCategory.do", "page", "previous");
		assertEquals(0, previous.value("productList.page"));
	}

	@Test
	public void showsProductWithItems() throws Exception {
		Page page = client.get("/shop/viewProduct.do", "productId", "FI-SW-01");
		assertEquals("Product", page.view());
		assertEquals("Angelfish", page.value("product.name"));
		assertEquals(2, page.value("itemList.nrOfElements"));
	}

	@Test
	public void showsItemWithStockAndProduct() throws Exception {
		Page page = client.get("/shop/viewItem.do", "itemId", "EST-1");
		assertEquals("Item", page.view());
		assertEquals(10000, page.value("item.quantity"));
		assertEquals(16.5, page.value("item.listPrice"));
		assertEquals("Angelfish", page.value("product.name"));
	}

	@Test
	public void searchesProductsIgnoringKeywordCase() throws Exception {
		Page page = client.post("/shop/searchProducts.do", "keyword", "GoldFish");
		assertEquals("SearchProducts", page.view());
		assertEquals(1, page.value("productList.nrOfElements"));
		assertEquals("FI-FW-02", page.value("productList.pageList[0].productId"));
	}

	@Test
	public void searchRequiresKeyword() throws Exception {
		Page page = client.post("/shop/searchProducts.do", "keyword", "");
		assertEquals("Error", page.view());
		assertEquals("Please enter a keyword to search for, then press the search button.", page.value("message"));
	}

	@Test
	public void searchPagingWithoutPreviousSearchReportsTimeout() throws Exception {
		Page page = client.get("/shop/searchProducts.do", "page", "next");
		assertEquals("Error", page.view());
		assertEquals("Your session has timed out. Please start over again.", page.value("message"));
	}

	@Test
	public void pagesThroughSearchResults() throws Exception {
		client.post("/shop/searchProducts.do", "keyword", "dog");
		Page next = client.get("/shop/searchProducts.do", "page", "next");
		assertEquals("SearchProducts", next.view());
		assertEquals(1, next.value("productList.page"));
	}
}
