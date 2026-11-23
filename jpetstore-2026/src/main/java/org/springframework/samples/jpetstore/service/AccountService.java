package org.springframework.samples.jpetstore.service;

import lombok.RequiredArgsConstructor;

import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.model.Account;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

	private final AccountDao accountDao;

	private final PasswordEncoder passwordEncoder;

	public Account getAccount(String username) {
		return accountDao.getAccount(username);
	}

	public Account getAccount(String username, String password) {
		if (username == null || password == null) {
			return null;
		}
		String encodedPassword = accountDao.getPassword(username);
		if (encodedPassword == null || !passwordEncoder.matches(password, encodedPassword)) {
			return null;
		}
		return accountDao.getAccount(username);
	}

	@Transactional
	public void insertAccount(Account account) {
		accountDao.insertAccount(account, passwordEncoder.encode(account.getPassword()));
	}

	@Transactional
	public void updateAccount(Account account) {
		boolean newPassword = account.getPassword() != null && !account.getPassword().isEmpty();
		accountDao.updateAccount(account, newPassword ? passwordEncoder.encode(account.getPassword()) : null);
	}
}
