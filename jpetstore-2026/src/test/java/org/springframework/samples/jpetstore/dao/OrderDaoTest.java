package org.springframework.samples.jpetstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.domain.Cart;
import org.springframework.samples.jpetstore.domain.LineItem;
import org.springframework.samples.jpetstore.domain.Order;

public class OrderDaoTest {

	private AccountDao accountDao;

	private ItemDao itemDao;

	private OrderDao orderDao;

	@Before
	public void setUp() throws Exception {
		TestApplication.reset();
		accountDao = TestApplication.bean(AccountDao.class);
		itemDao = TestApplication.bean(ItemDao.class);
		orderDao = TestApplication.bean(OrderDao.class);
	}

	private Order newOrder(String username, String itemId) {
		Cart cart = new Cart();
		cart.addItem(itemDao.getItem(itemId), true);
		Order order = new Order();
		order.initOrder(accountDao.getAccount(username), cart);
		return order;
	}

	@Test
	public void assignsOrderIdsFromSequenceStartingAt1000() {
		Order first = newOrder("j2ee", "EST-1");
		Order second = newOrder("j2ee", "EST-2");
		orderDao.insertOrder(first);
		orderDao.insertOrder(second);
		assertEquals(1000, first.getOrderId());
		assertEquals(1001, second.getOrderId());
		assertEquals(1000, ((LineItem) first.getLineItems().get(0)).getOrderId());
	}

	@Test
	public void loadsOrderWithStatusAndLineItems() {
		orderDao.insertOrder(newOrder("j2ee", "EST-1"));
		Order order = orderDao.getOrder(1000);
		assertEquals("j2ee", order.getUsername());
		assertEquals("P", order.getStatus());
		assertEquals("UPS", order.getCourier());
		assertEquals("Palo Alto", order.getShipCity());
		assertEquals(16.5, order.getTotalPrice(), 0.0001);
		assertEquals(1, order.getLineItems().size());
		LineItem lineItem = (LineItem) order.getLineItems().get(0);
		assertEquals(1, lineItem.getLineNumber());
		assertEquals("EST-1", lineItem.getItemId());
		assertEquals(1, lineItem.getQuantity());
		assertEquals(16.5, lineItem.getUnitPrice(), 0.0001);
		assertNull(lineItem.getItem());
		assertNull(orderDao.getOrder(9999));
	}

	@Test
	public void listsOrdersOfUser() {
		orderDao.insertOrder(newOrder("j2ee", "EST-1"));
		orderDao.insertOrder(newOrder("j2ee", "EST-2"));
		assertEquals(2, orderDao.getOrdersByUsername("j2ee").size());
		assertEquals(0, orderDao.getOrdersByUsername("ACID").size());
	}

	@Test
	public void updateQuantityDecrementsInventoryForEachLineItem() {
		Order order = newOrder("j2ee", "EST-1");
		((LineItem) order.getLineItems().get(0)).setQuantity(3);
		itemDao.updateQuantity(order);
		assertEquals(9997, itemDao.getItem("EST-1").getQuantity());
	}
}
