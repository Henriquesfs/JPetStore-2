package org.springframework.samples.jpetstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.dao.AccountDaoTest;
import org.springframework.samples.jpetstore.model.Account;

public class AccountServiceTest {

	private AccountService accountService;

	private AccountDao accountDao;

	@BeforeEach
	public void setUp() throws Exception {
		TestApplication.reset();
		accountService = TestApplication.bean(AccountService.class);
		accountDao = TestApplication.bean(AccountDao.class);
	}

	@Test
	public void authenticatesOnlyWithMatchingPassword() {
		assertEquals("j2ee", accountService.getAccount("j2ee", "j2ee").getUsername());
		assertNull(accountService.getAccount("j2ee", "wrong"));
		assertNull(accountService.getAccount("nobody", "j2ee"));
		assertNull(accountService.getAccount(null, null));
	}

	@Test
	public void newAccountPasswordIsStoredAsHash() {
		accountService.insertAccount(AccountDaoTest.newAccount("maria"));
		String stored = accountDao.getPassword("maria");
		assertNotEquals("secret", stored);
		assertTrue(stored.startsWith("$2a$"));
		assertNotNull(accountService.getAccount("maria", "secret"));
	}

	@Test
	public void updateWithoutPasswordKeepsCurrentPassword() {
		Account account = accountService.getAccount("j2ee");
		account.setFirstName("Zed");
		accountService.updateAccount(account);
		assertEquals("Zed", accountService.getAccount("j2ee", "j2ee").getFirstName());
	}

	@Test
	public void updateWithPasswordStoresNewHash() {
		Account account = accountService.getAccount("j2ee");
		account.setPassword("changed");
		accountService.updateAccount(account);
		assertNull(accountService.getAccount("j2ee", "j2ee"));
		assertNotNull(accountService.getAccount("j2ee", "changed"));
		assertNotEquals("changed", accountDao.getPassword("j2ee"));
	}
}
