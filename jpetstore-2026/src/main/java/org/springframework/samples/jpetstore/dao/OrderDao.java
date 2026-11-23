package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.samples.jpetstore.model.Order;

public interface OrderDao {

	List<Order> getOrdersByUsername(String username);

	Order getOrder(int orderId);

	void insertOrder(Order order);
}
