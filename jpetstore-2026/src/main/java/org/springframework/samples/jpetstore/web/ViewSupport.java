package org.springframework.samples.jpetstore.web;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.ui.Model;

final class ViewSupport {

	static final String USER_SESSION = "userSession";

	static final String CART = "sessionCart";

	private static final int PAGE_SIZE = 4;

	private ViewSupport() {
	}

	static PagedListHolder pagedList(List source) {
		PagedListHolder list = new PagedListHolder(source);
		list.setPageSize(PAGE_SIZE);
		return list;
	}

	static void turnPage(PagedListHolder list, String page) {
		turnPage(list, page, "next", "previous");
	}

	static void turnPage(PagedListHolder list, String page, String next, String previous) {
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

	static void signOn(HttpSession session, Account account, List favouriteProducts) {
		session.setAttribute(USER_SESSION, new UserSession(account, pagedList(favouriteProducts)));
	}
}
