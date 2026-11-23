package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.samples.jpetstore.model.Item;
import org.springframework.samples.jpetstore.model.Order;

public interface ItemDao {

	void updateQuantity(Order order);

	boolean isItemInStock(String itemId);

	List<Item> getItemListByProduct(String productId);

	Item getItem(String itemId);
}
