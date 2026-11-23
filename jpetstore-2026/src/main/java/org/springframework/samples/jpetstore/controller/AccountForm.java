package org.springframework.samples.jpetstore.controller;

import java.io.Serializable;

import org.springframework.samples.jpetstore.model.Account;

public class AccountForm implements Serializable {

	private final Account account;

	private final boolean newAccount;

	private String repeatedPassword;

	public AccountForm(Account account) {
		this.account = account;
		this.newAccount = false;
	}

	public AccountForm() {
		this.account = new Account();
		this.newAccount = true;
	}

	public Account getAccount() {
		return account;
	}

	public boolean isNewAccount() {
		return newAccount;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	boolean hasPassword() {
		return account.getPassword() != null && account.getPassword().length() > 0;
	}

	boolean passwordsMatch() {
		return hasPassword() && account.getPassword().equals(repeatedPassword);
	}
}
