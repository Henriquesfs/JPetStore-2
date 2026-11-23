package org.springframework.samples.jpetstore.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.samples.jpetstore.model.Account;
import org.springframework.samples.jpetstore.model.PagedList;
import org.springframework.samples.jpetstore.model.Product;
import org.springframework.ui.Model;

final class ViewSupport {

	static final String USER_SESSION = "userSession";

	static final String CART = "sessionCart";

	private static final int PAGE_SIZE = 4;

	private ViewSupport() {
	}

	static <E> PagedList<E> pagedList(List<E> source) {
		return new PagedList<>(source, PAGE_SIZE);
	}

	static void turnPage(PagedList<?> list, String page) {
		turnPage(list, page, "next", "previous");
	}

	static void turnPage(PagedList<?> list, String page, String next, String previous) {
		if (next.equals(page)) {
			list.nextPage();
		}
		else if (previous.equals(page)) {
			list.previousPage();
		}
	}

	static String error(Model model, String message) {
		model.addAttribute("message", message);
		return "Error";
	}

	static UserSession userSession(HttpSession session) {
		return (UserSession) session.getAttribute(USER_SESSION);
	}

	static void signOn(HttpSession session, Account account, List<Product> favouriteProducts) {
		session.setAttribute(USER_SESSION, new UserSession(account, pagedList(favouriteProducts)));
	}
}
