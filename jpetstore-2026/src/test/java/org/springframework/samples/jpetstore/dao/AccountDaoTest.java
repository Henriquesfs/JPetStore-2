package org.springframework.samples.jpetstore.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.model.Account;

public class AccountDaoTest {

	private AccountDao accountDao;

	@BeforeEach
	public void setUp() throws Exception {
		TestApplication.reset();
		accountDao = TestApplication.bean(AccountDao.class);
	}

	public static Account newAccount(String username) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword("secret");
		account.setEmail(username + "@example.com");
		account.setFirstName("Maria");
		account.setLastName("Silva");
		account.setStatus("OK");
		account.setAddress1("Rua A, 10");
		account.setCity("Recife");
		account.setState("PE");
		account.setZip("50000-000");
		account.setCountry("Brasil");
		account.setPhone("81 5555-5555");
		account.setLanguagePreference("english");
		account.setFavouriteCategoryId("FISH");
		account.setListOption(true);
		return account;
	}

	@Test
	public void loadsAccountWithProfileAndBanner() {
		Account account = accountDao.getAccount("j2ee");
		assertEquals("j2ee", account.getUsername());
		assertEquals("yourname@yourdomain.com", account.getEmail());
		assertEquals("ABC", account.getFirstName());
		assertEquals("Palo Alto", account.getCity());
		assertEquals("english", account.getLanguagePreference());
		assertEquals("DOGS", account.getFavouriteCategoryId());
		assertTrue(account.isListOption());
		assertTrue(account.isBannerOption());
		assertEquals("<image src=\"../images/banner_dogs.gif\">", account.getBannerName());
		assertNull(account.getPassword());
		assertNull(accountDao.getAccount("nobody"));
	}

	@Test
	public void storesSeedPasswordsAsBcryptHashes() {
		assertTrue(accountDao.getPassword("j2ee").startsWith("$2a$"));
		assertNull(accountDao.getPassword("nobody"));
	}

	@Test
	public void listsUsernames() {
		List<?> usernames = accountDao.getUsernameList();
		assertEquals(2, usernames.size());
		assertTrue(usernames.contains("j2ee"));
		assertTrue(usernames.contains("ACID"));
	}

	@Test
	public void insertsAccountProfileAndSignon() {
		accountDao.insertAccount(newAccount("maria"), "encoded");
		Account account = accountDao.getAccount("maria");
		assertEquals("maria@example.com", account.getEmail());
		assertEquals("FISH", account.getFavouriteCategoryId());
		assertTrue(account.isListOption());
		assertFalse(account.isBannerOption());
		assertEquals("encoded", accountDao.getPassword("maria"));
	}

	@Test
	public void rejectsDuplicateUsername() {
		assertThrows(DataIntegrityViolationException.class, () -> accountDao.insertAccount(newAccount("j2ee"), "encoded"));
	}

	@Test
	public void updateWithoutPasswordKeepsSignon() {
		String password = accountDao.getPassword("j2ee");
		Account account = accountDao.getAccount("j2ee");
		account.setFirstName("Zed");
		account.setListOption(false);
		accountDao.updateAccount(account, null);
		Account updated = accountDao.getAccount("j2ee");
		assertEquals("Zed", updated.getFirstName());
		assertFalse(updated.isListOption());
		assertEquals(password, accountDao.getPassword("j2ee"));
	}

	@Test
	public void updateWithPasswordChangesSignon() {
		accountDao.updateAccount(accountDao.getAccount("j2ee"), "changed");
		assertEquals("changed", accountDao.getPassword("j2ee"));
	}
}
