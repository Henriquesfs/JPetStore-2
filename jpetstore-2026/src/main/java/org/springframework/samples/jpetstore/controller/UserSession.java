package org.springframework.samples.jpetstore.controller;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.samples.jpetstore.model.Account;
import org.springframework.samples.jpetstore.model.PagedList;
import org.springframework.samples.jpetstore.model.Product;

@Getter
@RequiredArgsConstructor
public class UserSession implements Serializable {

	private final Account account;

	private final PagedList<Product> myList;
}
