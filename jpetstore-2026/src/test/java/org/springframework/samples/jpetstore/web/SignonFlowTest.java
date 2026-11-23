package org.springframework.samples.jpetstore.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.jpetstore.TestApplication;

public class SignonFlowTest {

	@Before
	public void setUp() throws Exception {
		TestApplication.reset();
	}

	@Test
	public void showsSignonForm() throws Exception {
		assertEquals("SignonForm", new ShopClient().get("/shop/signonForm.do").view());
	}

	@Test
	public void rejectsInvalidCredentials() throws Exception {
		ShopClient client = new ShopClient();
		Page page = client.post("/shop/signon.do", "username", "j2ee", "password", "wrong");
		assertEquals("Error", page.view());
		assertEquals("Invalid username or password.  Signon failed.", page.value("message"));
		assertNull(client.sessionValue("userSession"));
	}

	@Test
	public void signonStoresUserAndFavouriteProductsInSession() throws Exception {
		ShopClient client = new ShopClient();
		Page page = client.post("/shop/signon.do", "username", "j2ee", "password", "j2ee");
		assertEquals("index", page.view());
		assertEquals("j2ee", client.sessionValue("userSession.account.username"));
		assertEquals("ABC", client.sessionValue("userSession.account.firstName"));
		assertEquals(6, client.sessionValue("userSession.myList.nrOfElements"));
		assertEquals(4, client.sessionValue("userSession.myList.pageSize"));
	}

	@Test
	public void signonRedirectsToRequestedPage() throws Exception {
		Page page = new ShopClient().post("/shop/signon.do", "username", "j2ee", "password", "j2ee",
				"forwardAction", "/jpetstore/shop/listOrders.do");
		assertNull(page.view());
		assertEquals("/jpetstore/shop/listOrders.do", page.redirect());
	}

	@Test
	public void signoffEndsSession() throws Exception {
		ShopClient client = ShopClient.signedIn("j2ee", "j2ee");
		Page page = client.get("/shop/signoff.do");
		assertEquals("index", page.view());
		assertNull(client.sessionValue("userSession"));
	}

	@Test
	public void protectedPagesAskForSignonRememberingTheTarget() throws Exception {
		ShopClient client = new ShopClient();
		Page listOrders = client.get("/shop/listOrders.do");
		assertEquals("SignonForm", listOrders.view());
		assertEquals("/shop/listOrders.do", listOrders.value("signonForwardAction"));
		Page viewOrder = client.get("/shop/viewOrder.do", "orderId", "1000");
		assertEquals("SignonForm", viewOrder.view());
		assertEquals("/shop/viewOrder.do?orderId=1000", viewOrder.value("signonForwardAction"));
		assertEquals("SignonForm", client.get("/shop/editAccount.do").view());
		assertEquals("SignonForm", client.get("/shop/newOrder.do").view());
	}

	@Test
	public void favouriteListCanBePagedFromCartPage() throws Exception {
		ShopClient client = ShopClient.signedIn("j2ee", "j2ee");
		client.get("/shop/viewCart.do", "page", "next");
		assertEquals(1, client.sessionValue("userSession.myList.page"));
	}
}
