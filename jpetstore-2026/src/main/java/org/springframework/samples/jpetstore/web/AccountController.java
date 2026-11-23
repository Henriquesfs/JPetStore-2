package org.springframework.samples.jpetstore.web;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.logic.AccountValidator;
import org.springframework.samples.jpetstore.domain.logic.PetStoreFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("accountForm")
@RequestMapping({"/shop/newAccount.do", "/shop/editAccount.do"})
public class AccountController {

	private static final String[] LANGUAGES = {"english", "japanese"};

	private final PetStoreFacade petStore;

	private final AccountValidator accountValidator;

	@Autowired
	public AccountController(PetStoreFacade petStore, AccountValidator accountValidator) {
		this.petStore = petStore;
		this.accountValidator = accountValidator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpSession session, Model model) {
		UserSession userSession = ViewSupport.userSession(session);
		AccountForm accountForm = userSession != null
				? new AccountForm(petStore.getAccount(userSession.getAccount().getUsername()))
				: new AccountForm();
		model.addAttribute("accountForm", accountForm);
		return form(model);
	}

	@RequestMapping(method = RequestMethod.POST)
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
		Account saved = petStore.getAccount(account.getUsername());
		ViewSupport.signOn(session, saved, petStore.getProductListByCategory(account.getFavouriteCategoryId()));
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
			petStore.insertAccount(accountForm.getAccount());
		}
		else {
			petStore.updateAccount(accountForm.getAccount());
		}
	}

	private String form(Model model) {
		model.addAttribute("languages", LANGUAGES);
		model.addAttribute("categories", petStore.getCategoryList());
		return "EditAccountForm";
	}
}
