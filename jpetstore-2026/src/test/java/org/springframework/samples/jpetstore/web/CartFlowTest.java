package org.springframework.samples.jpetstore.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.jpetstore.TestApplication;

public class CartFlowTest {

	private ShopClient client;

	@Before
	public void setUp() throws Exception {
		TestApplication.reset();
		client = new ShopClient();
	}

	@Test
	public void emptyCartIsShown() throws Exception {
		Page page = client.get("/shop/viewCart.do");
		assertEquals("Cart", page.view());
		assertEquals(0, page.value("cart.numberOfItems"));
	}

	@Test
	public void addingItemShowsCartWithStockInformation() throws Exception {
		Page page = client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		assertEquals("Cart", page.view());
		assertEquals(1, page.value("cart.numberOfItems"));
		assertEquals(16.5, page.value("cart.subTotal"));
		assertEquals(Boolean.TRUE, page.value("cart.cartItemList.pageList[0].inStock"));
		assertEquals("Angelfish", page.value("cart.cartItemList.pageList[0].item.product.name"));
	}

	@Test
	public void addingSameItemTwiceIncrementsQuantity() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		Page page = client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		assertEquals(1, page.value("cart.numberOfItems"));
		assertEquals(2, page.value("cart.cartItemList.pageList[0].quantity"));
		assertEquals(33.0, page.value("cart.subTotal"));
	}

	@Test
	public void updatingQuantitiesChangesSubTotal() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		Page page = client.post("/shop/updateCartQuantities.do", "EST-1", "3");
		assertEquals("Cart", page.view());
		assertEquals(49.5, page.value("cart.subTotal"));
	}

	@Test
	public void invalidQuantityIsIgnored() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		Page page = client.post("/shop/updateCartQuantities.do", "EST-1", "abc");
		assertEquals(16.5, page.value("cart.subTotal"));
	}

	@Test
	public void quantityBelowOneRemovesItem() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-2");
		Page page = client.post("/shop/updateCartQuantities.do", "EST-1", "0", "EST-2", "1");
		assertEquals(1, page.value("cart.numberOfItems"));
		assertEquals("EST-2", page.value("cart.cartItemList.pageList[0].item.itemId"));
	}

	@Test
	public void removingItemEmptiesCart() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		Page page = client.get("/shop/removeItemFromCart.do", "workingItemId", "EST-1");
		assertEquals("Cart", page.view());
		assertEquals(0, page.value("cart.numberOfItems"));
	}

	@Test
	public void checkoutShowsSummaryOfCart() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		Page page = client.get("/shop/checkout.do");
		assertEquals("Checkout", page.view());
		assertEquals(16.5, page.value("cart.subTotal"));
	}

	@Test
	public void pagesThroughCartItems() throws Exception {
		for (String itemId : new String[] {"EST-1", "EST-2", "EST-3", "EST-4", "EST-5"}) {
			client.get("/shop/addItemToCart.do", "workingItemId", itemId);
		}
		Page page = client.get("/shop/viewCart.do", "page", "nextCart");
		assertEquals(1, page.value("cart.cartItemList.page"));
		assertEquals(1, ((java.util.List<?>) page.value("cart.cartItemList.pageList")).size());
	}
}
