package org.springframework.samples.jpetstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.service.AccountService;
import org.springframework.validation.Errors;

public class AccountFlowTest {

	private static final String[] NEW_ACCOUNT = {
			"account.username", "maria", "account.password", "secret", "repeatedPassword", "secret",
			"account.firstName", "Maria", "account.lastName", "Silva", "account.email", "maria@example.com",
			"account.phone", "81 5555-5555", "account.address1", "Rua A, 10", "account.address2", "",
			"account.city", "Recife", "account.state", "PE", "account.zip", "50000-000",
			"account.country", "Brasil", "account.languagePreference", "english",
			"account.favouriteCategoryId", "FISH", "account.listOption", "true"};

	@BeforeEach
	public void setUp() throws Exception {
		TestApplication.reset();
	}

	@Test
	public void newAccountFormOffersLanguagesAndCategories() throws Exception {
		Page page = new ShopClient().get("/shop/newAccount.do");
		assertEquals("EditAccountForm", page.view());
		assertEquals(Boolean.TRUE, page.value("accountForm.newAccount"));
		assertEquals(2, ((String[]) page.value("languages")).length);
		assertEquals(5, ((java.util.List<?>) page.value("categories")).size());
	}

	@Test
	public void creatingAccountSignsUserOn() throws Exception {
		ShopClient client = new ShopClient();
		client.get("/shop/newAccount.do");
		Page page = client.post("/shop/newAccount.do", NEW_ACCOUNT);
		assertEquals("index", page.view());
		assertEquals("maria", client.sessionValue("userSession.account.username"));
		assertEquals(4, client.sessionValue("userSession.myList.nrOfElements"));
		assertNotNull(TestApplication.bean(AccountService.class).getAccount("maria", "secret"));
	}

	@Test
	public void newAccountRequiresMatchingPasswords() throws Exception {
		ShopClient client = new ShopClient();
		client.get("/shop/newAccount.do");
		String[] parameters = NEW_ACCOUNT.clone();
		parameters[5] = "other";
		Page page = client.post("/shop/newAccount.do", parameters);
		assertEquals("EditAccountForm", page.view());
		assertEquals("PASSWORD_MISMATCH", page.errors("accountForm").getGlobalError().getCode());
	}

	@Test
	public void newAccountRequiresUsernameAndAccountFields() throws Exception {
		ShopClient client = new ShopClient();
		client.get("/shop/newAccount.do");
		Page page = client.post("/shop/newAccount.do", "account.username", "", "account.password", "x",
				"repeatedPassword", "x");
		Errors errors = page.errors("accountForm");
		assertEquals("EditAccountForm", page.view());
		assertEquals("USER_ID_REQUIRED", errors.getFieldError("account.username").getCode());
		assertEquals("FIRST_NAME_REQUIRED", errors.getFieldError("account.firstName").getCode());
		assertEquals(10, errors.getErrorCount());
	}

	@Test
	public void duplicateUsernameIsReportedOnForm() throws Exception {
		ShopClient client = new ShopClient();
		client.get("/shop/newAccount.do");
		String[] parameters = NEW_ACCOUNT.clone();
		parameters[1] = "j2ee";
		Page page = client.post("/shop/newAccount.do", parameters);
		assertEquals("EditAccountForm", page.view());
		assertEquals("USER_ID_ALREADY_EXISTS", page.errors("accountForm").getFieldError("account.username").getCode());
	}

	@Test
	public void editAccountFormIsPrefilled() throws Exception {
		Page page = ShopClient.signedIn("j2ee", "j2ee").get("/shop/editAccount.do");
		assertEquals("EditAccountForm", page.view());
		assertEquals(Boolean.FALSE, page.value("accountForm.newAccount"));
		assertEquals("ABC", page.value("accountForm.account.firstName"));
	}

	@Test
	public void editingAccountUpdatesDatabaseAndSession() throws Exception {
		ShopClient client = ShopClient.signedIn("j2ee", "j2ee");
		client.get("/shop/editAccount.do");
		Page page = client.post("/shop/editAccount.do", "account.firstName", "Zed", "account.password", "",
				"account.bannerOption", "true");
		assertEquals("index", page.view());
		assertEquals("Zed", client.sessionValue("userSession.account.firstName"));
		AccountDao accountDao = TestApplication.bean(AccountDao.class);
		assertEquals("Zed", TestApplication.bean(AccountService.class).getAccount("j2ee", "j2ee").getFirstName());
		assertFalse(accountDao.getAccount("j2ee").isListOption());
		assertTrue(accountDao.getAccount("j2ee").isBannerOption());
	}

	@Test
	public void editingAccountWithDifferentPasswordsIsRejected() throws Exception {
		ShopClient client = ShopClient.signedIn("j2ee", "j2ee");
		client.get("/shop/editAccount.do");
		Page page = client.post("/shop/editAccount.do", "account.password", "new", "repeatedPassword", "other");
		assertEquals("EditAccountForm", page.view());
		assertEquals("PASSWORD_MISMATCH", page.errors("accountForm").getGlobalError().getCode());
	}
}
