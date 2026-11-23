package org.springframework.samples.jpetstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.dao.ItemDao;

public class OrderFlowTest {

	private ShopClient client;

	@BeforeEach
	public void setUp() throws Exception {
		TestApplication.reset();
		client = ShopClient.signedIn("j2ee", "j2ee");
	}

	private Page placeOrder(ShopClient buyer) throws Exception {
		buyer.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		buyer.get("/shop/newOrder.do");
		buyer.post("/shop/newOrder.do");
		return buyer.get("/shop/newOrder.do", "_finish", "true");
	}

	@Test
	public void orderWithoutCartIsRejected() throws Exception {
		Page page = client.get("/shop/newOrder.do");
		assertEquals("Error", page.view());
		assertEquals("An order could not be created because a cart could not be found.", page.value("message"));
	}

	@Test
	public void paymentFormIsPrefilledFromAccount() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		Page page = client.get("/shop/newOrder.do");
		assertEquals("NewOrderForm", page.view());
		assertEquals(3, ((java.util.List<?>) page.value("creditCardTypes")).size());
		assertEquals("ABC", page.value("orderForm.order.billToFirstName"));
		assertEquals("999 9999 9999 9999", page.value("orderForm.order.creditCard"));
		assertEquals(16.5, page.value("orderForm.order.totalPrice"));
	}

	@Test
	public void missingPaymentDataKeepsPaymentForm() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		client.get("/shop/newOrder.do");
		Page page = client.post("/shop/newOrder.do", "order.creditCard", "", "order.billCity", "");
		assertEquals("NewOrderForm", page.view());
		assertEquals("CCN_REQUIRED", page.errors("orderForm").getFieldError("order.creditCard").getCode());
		assertEquals("CITY_REQUIRED", page.errors("orderForm").getFieldError("order.billCity").getCode());
		assertEquals(3, ((java.util.List<?>) page.value("creditCardTypes")).size());
	}

	@Test
	public void paymentGoesStraightToConfirmationWhenShippingToBillingAddress() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		client.get("/shop/newOrder.do");
		Page page = client.post("/shop/newOrder.do", "order.billToFirstName", "Ana");
		assertEquals("ConfirmOrder", page.view());
		assertEquals("Ana", page.value("orderForm.order.billToFirstName"));
	}

	@Test
	public void differentShippingAddressAddsShippingStep() throws Exception {
		client.get("/shop/addItemToCart.do", "workingItemId", "EST-1");
		client.get("/shop/newOrder.do");
		Page shipping = client.post("/shop/newOrder.do", "shippingAddressRequired", "true");
		assertEquals("ShippingForm", shipping.view());
		Page invalid = client.post("/shop/newOrder.do", "order.shipCity", "");
		assertEquals("ShippingForm", invalid.view());
		assertEquals("CITY_REQUIRED", invalid.errors("orderForm").getFieldError("order.shipCity").getCode());
		Page confirm = client.post("/shop/newOrder.do", "order.shipCity", "Recife");
		assertEquals("ConfirmOrder", confirm.view());
		assertEquals("Recife", confirm.value("orderForm.order.shipCity"));
	}

	@Test
	public void finishingOrderStoresItEmptiesCartAndUpdatesInventory() throws Exception {
		Page page = placeOrder(client);
		assertEquals("ViewOrder", page.view());
		assertEquals("Thank you, your order has been submitted.", page.value("message"));
		assertEquals(1000, page.value("order.orderId"));
		assertEquals("Angelfish", page.value("order.lineItems[0].item.product.name"));
		assertNull(client.sessionValue("sessionCart"));
		assertEquals(9999, TestApplication.bean(ItemDao.class).getItem("EST-1").getQuantity());
	}

	@Test
	public void listsAndShowsOwnOrders() throws Exception {
		placeOrder(client);
		Page list = client.get("/shop/listOrders.do");
		assertEquals("ListOrders", list.view());
		assertEquals(1, ((java.util.List<?>) list.value("orderList")).size());
		Page order = client.get("/shop/viewOrder.do", "orderId", "1000");
		assertEquals("ViewOrder", order.view());
		assertEquals("j2ee", order.value("order.username"));
		assertEquals(1, ((java.util.List<?>) order.value("order.lineItems")).size());
		assertNull(order.value("message"));
	}

	@Test
	public void ordersOfOtherUsersAreHidden() throws Exception {
		placeOrder(client);
		Page page = ShopClient.signedIn("ACID", "ACID").get("/shop/viewOrder.do", "orderId", "1000");
		assertEquals("Error", page.view());
		assertEquals("You may only view your own orders.", page.value("message"));
	}
}
