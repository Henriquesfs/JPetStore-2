package org.springframework.samples.jpetstore.web;

import java.io.Serializable;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;

public class UserSession implements Serializable {

	private final Account account;

	private final PagedListHolder myList;

	public UserSession(Account account, PagedListHolder myList) {
		this.account = account;
		this.myList = myList;
	}

	public Account getAccount() {
		return account;
	}

	public PagedListHolder getMyList() {
		return myList;
	}
}
