package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.samples.jpetstore.model.Account;

public interface AccountDao {

	Account getAccount(String username);

	String getPassword(String username);

	void insertAccount(Account account, String encodedPassword);

	void updateAccount(Account account, String encodedPassword);

	List<String> getUsernameList();
}
