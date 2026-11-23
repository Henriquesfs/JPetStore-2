package org.springframework.samples.jpetstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cart implements Serializable {

	private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<>());

	private final PagedList<CartItem> itemList = new PagedList<>(new ArrayList<>(), 4);

	public Iterator<CartItem> getAllCartItems() {
		return itemList.getSource().iterator();
	}

	public PagedList<CartItem> getCartItemList() {
		return itemList;
	}

	public int getNumberOfItems() {
		return itemList.getSource().size();
	}

	public boolean containsItemId(String itemId) {
		return itemMap.containsKey(itemId);
	}

	public void addItem(Item item, boolean isInStock) {
		CartItem cartItem = itemMap.get(item.getItemId());
		if (cartItem == null) {
			cartItem = new CartItem();
			cartItem.setItem(item);
			cartItem.setQuantity(0);
			cartItem.setInStock(isInStock);
			itemMap.put(item.getItemId(), cartItem);
			itemList.getSource().add(cartItem);
		}
		cartItem.incrementQuantity();
	}

	public Item removeItemById(String itemId) {
		CartItem cartItem = itemMap.remove(itemId);
		if (cartItem == null) {
			return null;
		}
		itemList.getSource().remove(cartItem);
		return cartItem.getItem();
	}

	public void incrementQuantityByItemId(String itemId) {
		itemMap.get(itemId).incrementQuantity();
	}

	public void setQuantityByItemId(String itemId, int quantity) {
		itemMap.get(itemId).setQuantity(quantity);
	}

	public void updateQuantity(String itemId, int quantity) {
		if (quantity < 1) {
			removeItemById(itemId);
		}
		else {
			setQuantityByItemId(itemId, quantity);
		}
	}

	public double getSubTotal() {
		double subTotal = 0;
		for (CartItem cartItem : itemList.getSource()) {
			subTotal += cartItem.getTotalPrice();
		}
		return subTotal;
	}
}
