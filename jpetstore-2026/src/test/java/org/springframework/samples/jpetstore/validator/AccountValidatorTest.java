package org.springframework.samples.jpetstore.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.samples.jpetstore.model.Account;
import org.springframework.samples.jpetstore.model.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

public class AccountValidatorTest {

	private final AccountValidator validator = new AccountValidator();

	@Test
	public void supportsOnlyAccounts() {
		assertTrue(validator.supports(Account.class));
		assertFalse(validator.supports(Order.class));
	}

	@Test
	public void emptyAccountRejectsEveryRequiredField() {
		Account account = new Account();
		Errors errors = new BindException(account, "account");
		validator.validate(account, errors);
		assertEquals(9, errors.getErrorCount());
		assertEquals("FIRST_NAME_REQUIRED", errors.getFieldError("firstName").getCode());
		assertEquals("LAST_NAME_REQUIRED", errors.getFieldError("lastName").getCode());
		assertEquals("EMAIL_REQUIRED", errors.getFieldError("email").getCode());
		assertEquals("PHONE_REQUIRED", errors.getFieldError("phone").getCode());
		assertEquals("ADDRESS_REQUIRED", errors.getFieldError("address1").getCode());
		assertEquals("CITY_REQUIRED", errors.getFieldError("city").getCode());
		assertEquals("STATE_REQUIRED", errors.getFieldError("state").getCode());
		assertEquals("ZIP_REQUIRED", errors.getFieldError("zip").getCode());
		assertEquals("COUNTRY_REQUIRED", errors.getFieldError("country").getCode());
	}

	@Test
	public void completeAccountIsValid() {
		Account account = new Account();
		account.setFirstName("Maria");
		account.setLastName("Silva");
		account.setEmail("maria@example.com");
		account.setPhone("81 5555-5555");
		account.setAddress1("Rua A, 10");
		account.setCity("Recife");
		account.setState("PE");
		account.setZip("50000-000");
		account.setCountry("Brasil");
		Errors errors = new BindException(account, "account");
		validator.validate(account, errors);
		assertFalse(errors.hasErrors());
	}
}
