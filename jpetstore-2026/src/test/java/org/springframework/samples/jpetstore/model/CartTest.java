package org.springframework.samples.jpetstore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CartTest {

	static Item item(String itemId, double listPrice) {
		Item item = new Item();
		item.setItemId(itemId);
		item.setProductId("FI-SW-01");
		item.setListPrice(listPrice);
		return item;
	}

	@Test
	public void addingNewItemStartsWithQuantityOne() {
		Cart cart = new Cart();
		cart.addItem(item("EST-1", 16.5), true);
		CartItem cartItem = (CartItem) cart.getAllCartItems().next();
		assertTrue(cart.containsItemId("EST-1"));
		assertEquals(1, cart.getNumberOfItems());
		assertEquals(1, cartItem.getQuantity());
		assertTrue(cartItem.isInStock());
	}

	@Test
	public void addingSameItemIncrementsQuantityAndKeepsFirstStockFlag() {
		Cart cart = new Cart();
		Item item = item("EST-1", 16.5);
		cart.addItem(item, true);
		cart.addItem(item, false);
		CartItem cartItem = (CartItem) cart.getAllCartItems().next();
		assertEquals(1, cart.getNumberOfItems());
		assertEquals(2, cartItem.getQuantity());
		assertTrue(cartItem.isInStock());
	}

	@Test
	public void incrementAndSetQuantityByItemId() {
		Cart cart = new Cart();
		cart.addItem(item("EST-1", 16.5), true);
		cart.incrementQuantityByItemId("EST-1");
		CartItem cartItem = (CartItem) cart.getAllCartItems().next();
		assertEquals(2, cartItem.getQuantity());
		cart.setQuantityByItemId("EST-1", 7);
		assertEquals(7, cartItem.getQuantity());
	}

	@Test
	public void subTotalMultipliesListPriceByQuantity() {
		Cart cart = new Cart();
		cart.addItem(item("EST-1", 16.5), true);
		cart.addItem(item("EST-2", 10.0), true);
		cart.setQuantityByItemId("EST-1", 3);
		assertEquals(59.5, cart.getSubTotal(), 0.0001);
	}

	@Test
	public void removingItemReturnsItOnceAndThenNull() {
		Cart cart = new Cart();
		cart.addItem(item("EST-1", 16.5), true);
		assertEquals("EST-1", cart.removeItemById("EST-1").getItemId());
		assertNull(cart.removeItemById("EST-1"));
		assertFalse(cart.containsItemId("EST-1"));
		assertEquals(0, cart.getNumberOfItems());
	}

	@Test
	public void quantityBelowOneRemovesItemCompletely() {
		Cart cart = new Cart();
		cart.addItem(item("EST-1", 16.5), true);
		cart.addItem(item("EST-2", 10.0), true);
		cart.updateQuantity("EST-1", 0);
		cart.updateQuantity("EST-2", 3);
		assertFalse(cart.containsItemId("EST-1"));
		assertEquals(1, cart.getNumberOfItems());
		assertEquals(30.0, cart.getSubTotal(), 0.0001);
	}

	@Test
	public void cartItemsArePagedByFour() {
		Cart cart = new Cart();
		for (int i = 1; i <= 5; i++) {
			cart.addItem(item("EST-" + i, 1.0), true);
		}
		assertEquals(4, cart.getCartItemList().getPageList().size());
		cart.getCartItemList().nextPage();
		assertEquals(1, cart.getCartItemList().getPageList().size());
	}

	@Test
	public void cartItemTotalPriceIsZeroWithoutItem() {
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(3);
		assertEquals(0.0, cartItem.getTotalPrice(), 0.0001);
		cartItem.setItem(item("EST-1", 2.5));
		assertEquals(7.5, cartItem.getTotalPrice(), 0.0001);
	}

	@Test
	public void identifiersAreTrimmed() {
		Category category = new Category();
		category.setCategoryId("FISH  ");
		Product product = new Product();
		product.setProductId(" FI-SW-01 ");
		assertEquals("FISH", category.getCategoryId());
		assertEquals("FI-SW-01", product.getProductId());
		assertEquals("EST-1", item(" EST-1 ", 1.0).getItemId());
	}
}
