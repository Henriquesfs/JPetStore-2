package org.springframework.samples.jpetstore.web;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.logic.PetStoreFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignonController {

	private final PetStoreFacade petStore;

	@Autowired
	public SignonController(PetStoreFacade petStore) {
		this.petStore = petStore;
	}

	@RequestMapping("/shop/signonForm.do")
	public String signonForm() {
		return "SignonForm";
	}

	@RequestMapping("/shop/signon.do")
	public String signon(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String forwardAction, HttpSession session,
			HttpServletResponse response, Model model) throws IOException {
		Account account = petStore.getAccount(username, password);
		if (account == null) {
			return ViewSupport.error(model, "Invalid username or password.  Signon failed.");
		}
		ViewSupport.signOn(session, account, petStore.getProductListByCategory(account.getFavouriteCategoryId()));
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
