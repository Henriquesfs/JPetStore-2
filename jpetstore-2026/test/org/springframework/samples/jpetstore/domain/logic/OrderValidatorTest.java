package org.springframework.samples.jpetstore.domain.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Cart;
import org.springframework.samples.jpetstore.domain.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

public class OrderValidatorTest {

	private final OrderValidator validator = new OrderValidator();

	@Test
	public void supportsOnlyOrders() {
		assertTrue(validator.supports(Order.class));
		assertFalse(validator.supports(Account.class));
	}

	@Test
	public void emptyOrderRejectsPaymentBillingAndShipping() {
		Order order = new Order();
		Errors errors = new BindException(order, "order");
		validator.validate(order, errors);
		assertEquals(17, errors.getErrorCount());
		assertEquals("CCN_REQUIRED", errors.getFieldError("creditCard").getCode());
		assertEquals("EXPIRY_DATE_REQUIRED", errors.getFieldError("expiryDate").getCode());
		assertEquals("CARD_TYPE_REQUIRED", errors.getFieldError("cardType").getCode());
		assertEquals("FIRST_NAME_REQUIRED", errors.getFieldError("billToFirstName").getCode());
		assertEquals("COUNTRY_REQUIRED", errors.getFieldError("shipCountry").getCode());
	}

	@Test
	public void eachSectionCanBeValidatedSeparately() {
		Order order = new Order();
		Errors payment = new BindException(order, "order");
		validator.validateCreditCard(order, payment);
		assertEquals(3, payment.getErrorCount());
		Errors billing = new BindException(order, "order");
		validator.validateBillingAddress(order, billing);
		assertEquals(7, billing.getErrorCount());
		Errors shipping = new BindException(order, "order");
		validator.validateShippingAddress(order, shipping);
		assertEquals(7, shipping.getErrorCount());
	}

	@Test
	public void initializedOrderIsValid() {
		Account account = new Account();
		account.setUsername("j2ee");
		account.setFirstName("ABC");
		account.setLastName("XYX");
		account.setAddress1("901 San Antonio Road");
		account.setCity("Palo Alto");
		account.setState("CA");
		account.setZip("94303");
		account.setCountry("USA");
		Order order = new Order();
		order.initOrder(account, new Cart());
		Errors errors = new BindException(order, "order");
		validator.validate(order, errors);
		assertFalse(errors.hasErrors());
	}
}
