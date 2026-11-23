package org.springframework.samples.jpetstore.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.samples.jpetstore.model.Account;
import org.springframework.samples.jpetstore.service.AccountService;
import org.springframework.samples.jpetstore.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SignonController {

	private final AccountService accounts;

	private final CatalogService catalog;

	@RequestMapping("/shop/signonForm.do")
	public String signonForm() {
		return "SignonForm";
	}

	@RequestMapping("/shop/signon.do")
	public String signon(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String forwardAction, HttpSession session,
			HttpServletResponse response, Model model) throws IOException {
		Account account = accounts.getAccount(username, password);
		if (account == null) {
			return ViewSupport.error(model, "Invalid username or password.  Signon failed.");
		}
		ViewSupport.signOn(session, account, catalog.getProductListByCategory(account.getFavouriteCategoryId()));
		if (forwardAction != null) {
			response.sendRedirect(forwardAction);
			return null;
		}
		return "index";
	}

	@RequestMapping("/shop/signoff.do")
	public String signoff(HttpSession session) {
		session.removeAttribute(ViewSupport.USER_SESSION);
		session.invalidate();
		return "index";
	}
}
