package org.springframework.samples.jpetstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.domain.Category;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.Product;

public class CatalogDaoTest {

	private CategoryDao categoryDao;

	private ProductDao productDao;

	private ItemDao itemDao;

	@Before
	public void setUp() throws Exception {
		TestApplication.reset();
		categoryDao = TestApplication.bean(CategoryDao.class);
		productDao = TestApplication.bean(ProductDao.class);
		itemDao = TestApplication.bean(ItemDao.class);
	}

	@Test
	public void listsAllFiveCategories() {
		assertEquals(5, categoryDao.getCategoryList().size());
	}

	@Test
	public void findsCategoryById() {
		Category category = categoryDao.getCategory("FISH");
		assertEquals("FISH", category.getCategoryId());
		assertEquals("Fish", category.getName());
		assertNull(categoryDao.getCategory("NONE"));
	}

	@Test
	public void listsProductsOfCategory() {
		assertEquals(Arrays.asList("FI-FW-01", "FI-FW-02", "FI-SW-01", "FI-SW-02"),
				productIds(productDao.getProductListByCategory("FISH")));
		assertEquals(6, productDao.getProductListByCategory("DOGS").size());
	}

	@Test
	public void findsProductById() {
		Product product = productDao.getProduct("K9-BD-01");
		assertEquals("Bulldog", product.getName());
		assertEquals("DOGS", product.getCategoryId());
		assertNull(productDao.getProduct("NONE"));
	}

	@Test
	public void searchMatchesNameCategoryOrDescription() {
		assertEquals(Arrays.asList("FI-FW-02"), productIds(productDao.searchProductList("goldfish")));
		assertEquals(4, productDao.searchProductList("fish").size());
		assertEquals(Arrays.asList("FI-FW-01", "K9-BD-01"), productIds(productDao.searchProductList("koi bulldog")));
	}

	@Test
	public void searchComparesKeywordAsGivenAgainstLowerCaseColumns() {
		assertTrue(productDao.searchProductList("Goldfish").isEmpty());
	}

	@Test
	public void listsItemsOfProductWithTheirProduct() {
		List<?> items = itemDao.getItemListByProduct("FI-SW-01");
		assertEquals(2, items.size());
		Item item = (Item) items.get(0);
		assertEquals("FI-SW-01", item.getProductId());
		assertEquals("Angelfish", item.getProduct().getName());
		assertEquals("FISH", item.getProduct().getCategoryId());
	}

	@Test
	public void findsItemWithInventoryQuantity() {
		Item item = itemDao.getItem("EST-1");
		assertEquals("EST-1", item.getItemId());
		assertEquals(16.5, item.getListPrice(), 0.0001);
		assertEquals(10.0, item.getUnitCost(), 0.0001);
		assertEquals("Large", item.getAttribute1());
		assertEquals(10000, item.getQuantity());
		assertEquals("Angelfish", item.getProduct().getName());
		assertNull(itemDao.getItem("NONE"));
	}

	@Test
	public void reportsStock() {
		assertTrue(itemDao.isItemInStock("EST-1"));
		assertFalse(itemDao.isItemInStock("NONE"));
	}

	private static List<String> productIds(List<?> products) {
		List<String> ids = new ArrayList<String>();
		for (Object product : products) {
			ids.add(((Product) product).getProductId());
		}
		Collections.sort(ids);
		return ids;
	}
}
