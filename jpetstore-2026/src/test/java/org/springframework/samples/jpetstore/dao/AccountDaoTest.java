package org.springframework.samples.jpetstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.jpetstore.TestApplication;
import org.springframework.samples.jpetstore.domain.Account;

public class AccountDaoTest {

	private AccountDao accountDao;

	@Before
	public void setUp() throws Exception {
		TestApplication.reset();
		accountDao = TestApplication.bean(AccountDao.class);
	}

	static Account newAccount(String username) {
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
	public void authenticatesOnlyWithMatchingPassword() {
		assertNotNull(accountDao.getAccount("j2ee", "j2ee"));
		assertNull(accountDao.getAccount("j2ee", "wrong"));
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
		accountDao.insertAccount(newAccount("maria"));
		Account account = accountDao.getAccount("maria", "secret");
		assertEquals("maria@example.com", account.getEmail());
		assertEquals("FISH", account.getFavouriteCategoryId());
		assertTrue(account.isListOption());
		assertFalse(account.isBannerOption());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void rejectsDuplicateUsername() {
		accountDao.insertAccount(newAccount("j2ee"));
	}

	@Test
	public void updateWithoutPasswordKeepsSignon() {
		Account account = accountDao.getAccount("j2ee");
		account.setFirstName("Zed");
		account.setListOption(false);
		accountDao.updateAccount(account);
		Account updated = accountDao.getAccount("j2ee", "j2ee");
		assertEquals("Zed", updated.getFirstName());
		assertFalse(updated.isListOption());
	}

	@Test
	public void updateWithPasswordChangesSignon() {
		Account account = accountDao.getAccount("j2ee");
		account.setPassword("changed");
		accountDao.updateAccount(account);
		assertNull(accountDao.getAccount("j2ee", "j2ee"));
		assertNotNull(accountDao.getAccount("j2ee", "changed"));
	}
}
