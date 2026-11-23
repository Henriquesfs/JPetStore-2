package org.springframework.samples.jpetstore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class OrderTest {

	static Account account() {
		Account account = new Account();
		account.setUsername("j2ee");
		account.setFirstName("ABC");
		account.setLastName("XYX");
		account.setEmail("yourname@yourdomain.com");
		account.setPhone("555-555-5555");
		account.setAddress1("901 San Antonio Road");
		account.setAddress2("MS UCUP02-206");
		account.setCity("Palo Alto");
		account.setState("CA");
		account.setZip("94303");
		account.setCountry("USA");
		return account;
	}

	@Test
	public void initOrderCopiesAccountIntoShippingAndBilling() {
		Order order = new Order();
		order.initOrder(account(), new Cart());
		assertEquals("j2ee", order.getUsername());
		assertNotNull(order.getOrderDate());
		assertEquals("ABC", order.getShipToFirstName());
		assertEquals("XYX", order.getShipToLastName());
		assertEquals("901 San Antonio Road", order.getShipAddress1());
		assertEquals("MS UCUP02-206", order.getShipAddress2());
		assertEquals("Palo Alto", order.getShipCity());
		assertEquals("CA", order.getShipState());
		assertEquals("94303", order.getShipZip());
		assertEquals("USA", order.getShipCountry());
		assertEquals("ABC", order.getBillToFirstName());
		assertEquals("XYX", order.getBillToLastName());
		assertEquals("901 San Antonio Road", order.getBillAddress1());
		assertEquals("MS UCUP02-206", order.getBillAddress2());
		assertEquals("Palo Alto", order.getBillCity());
		assertEquals("CA", order.getBillState());
		assertEquals("94303", order.getBillZip());
		assertEquals("USA", order.getBillCountry());
	}

	@Test
	public void initOrderUsesFixedPaymentAndShippingDefaults() {
		Order order = new Order();
		order.initOrder(account(), new Cart());
		assertEquals("999 9999 9999 9999", order.getCreditCard());
		assertEquals("12/03", order.getExpiryDate());
		assertEquals("Visa", order.getCardType());
		assertEquals("UPS", order.getCourier());
		assertEquals("CA", order.getLocale());
		assertEquals("P", order.getStatus());
	}

	@Test
	public void initOrderCreatesNumberedLineItemsFromCart() {
		Cart cart = new Cart();
		Item first = CartTest.item("EST-1", 16.5);
		cart.addItem(first, true);
		cart.addItem(first, true);
		cart.addItem(CartTest.item("EST-2", 10.0), true);
		Order order = new Order();
		order.initOrder(account(), cart);
		assertEquals(43.0, order.getTotalPrice(), 0.0001);
		assertEquals(2, order.getLineItems().size());
		LineItem firstLine = (LineItem) order.getLineItems().get(0);
		LineItem secondLine = (LineItem) order.getLineItems().get(1);
		assertEquals(1, firstLine.getLineNumber());
		assertEquals("EST-1", firstLine.getItemId());
		assertEquals(2, firstLine.getQuantity());
		assertEquals(16.5, firstLine.getUnitPrice(), 0.0001);
		assertEquals(33.0, firstLine.getTotalPrice(), 0.0001);
		assertEquals(2, secondLine.getLineNumber());
		assertEquals("EST-2", secondLine.getItemId());
	}
}
