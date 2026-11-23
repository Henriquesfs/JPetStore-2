package org.springframework.samples.jpetstore.controller;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.jpetstore.model.Account;
import org.springframework.samples.jpetstore.service.AccountService;
import org.springframework.samples.jpetstore.service.CatalogService;
import org.springframework.samples.jpetstore.validator.AccountValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("accountForm")
@RequestMapping({"/shop/newAccount.do", "/shop/editAccount.do"})
@RequiredArgsConstructor
public class AccountController {

	private static final String[] LANGUAGES = {"english", "japanese"};

	private final AccountService accounts;

	private final CatalogService catalog;

	private final AccountValidator accountValidator;

	@GetMapping
	public String showForm(HttpSession session, Model model) {
		UserSession userSession = ViewSupport.userSession(session);
		AccountForm accountForm = userSession != null
				? new AccountForm(accounts.getAccount(userSession.getAccount().getUsername()))
				: new AccountForm();
		model.addAttribute("accountForm", accountForm);
		return form(model);
	}

	@PostMapping
	public String submit(@ModelAttribute("accountForm") AccountForm accountForm, BindingResult result,
			@RequestParam(value = "account.listOption", required = false) String listOption,
			@RequestParam(value = "account.bannerOption", required = false) String bannerOption,
			SessionStatus status, HttpSession session, Model model) {
		Account account = accountForm.getAccount();
		account.setListOption(listOption != null && account.isListOption());
		account.setBannerOption(bannerOption != null && account.isBannerOption());
		validate(accountForm, result);
		if (result.hasErrors()) {
			return form(model);
		}
		try {
			save(accountForm);
		}
		catch (DataIntegrityViolationException ex) {
			result.rejectValue("account.username", "USER_ID_ALREADY_EXISTS", "User ID already exists: choose a different ID.");
			return form(model);
		}
		Account saved = accounts.getAccount(account.getUsername());
		ViewSupport.signOn(session, saved, catalog.getProductListByCategory(account.getFavouriteCategoryId()));
		status.setComplete();
		return "index";
	}

	private void validate(AccountForm accountForm, BindingResult result) {
		result.pushNestedPath("account");
		accountValidator.validate(accountForm.getAccount(), result);
		result.popNestedPath();
		if (accountForm.isNewAccount()) {
			accountForm.getAccount().setStatus("OK");
			ValidationUtils.rejectIfEmpty(result, "account.username", "USER_ID_REQUIRED", "User ID is required.");
			if (!accountForm.passwordsMatch()) {
				result.reject("PASSWORD_MISMATCH", "Passwords did not match or were not provided. Matching passwords are required.");
			}
		}
		else if (accountForm.hasPassword() && !accountForm.passwordsMatch()) {
			result.reject("PASSWORD_MISMATCH", "Passwords did not match. Matching passwords are required.");
		}
	}

	private void save(AccountForm accountForm) {
		if (accountForm.isNewAccount()) {
			accounts.insertAccount(accountForm.getAccount());
		}
		else {
			accounts.updateAccount(accountForm.getAccount());
		}
	}

	private String form(Model model) {
		model.addAttribute("languages", LANGUAGES);
		model.addAttribute("categories", catalog.getCategoryList());
		return "EditAccountForm";
	}
}
