package org.springframework.samples.jpetstore.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.samples.jpetstore.dao.ItemDao;
import org.springframework.samples.jpetstore.dao.OrderDao;
import org.springframework.samples.jpetstore.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderDao orderDao;

	private final ItemDao itemDao;

	@Transactional
	public void insertOrder(Order order) {
		orderDao.insertOrder(order);
		itemDao.updateQuantity(order);
	}

	public Order getOrder(int orderId) {
		return orderDao.getOrder(orderId);
	}

	public List<Order> getOrdersByUsername(String username) {
		return orderDao.getOrdersByUsername(username);
	}
}
