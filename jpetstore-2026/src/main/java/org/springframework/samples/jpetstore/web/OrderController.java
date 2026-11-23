package org.springframework.samples.jpetstore.web;

import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Cart;
import org.springframework.samples.jpetstore.domain.Order;
import org.springframework.samples.jpetstore.domain.logic.OrderValidator;
import org.springframework.samples.jpetstore.domain.logic.PetStoreFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("orderForm")
public class OrderController {

	private static final List<String> CREDIT_CARD_TYPES = Arrays.asList("Visa", "MasterCard", "American Express");

	private static final String[] PAGES = {"NewOrderForm", "ShippingForm", "ConfirmOrder"};

	private static final int PAYMENT = 0;

	private static final int SHIPPING = 1;

	private static final int CONFIRMATION = 2;

	private final PetStoreFacade petStore;

	private final OrderValidator orderValidator;

	@Autowired
	public OrderController(PetStoreFacade petStore, OrderValidator orderValidator) {
		this.petStore = petStore;
		this.orderValidator = orderValidator;
	}

	@RequestMapping(value = "/shop/newOrder.do", method = RequestMethod.GET, params = "!_finish")
	public String newOrder(HttpSession session, Model model) {
		Cart cart = (Cart) session.getAttribute(ViewSupport.CART);
		if (cart == null) {
			return ViewSupport.error(model, "An order could not be created because a cart could not be found.");
		}
		Account account = petStore.getAccount(ViewSupport.userSession(session).getAccount().getUsername());
		OrderForm orderForm = new OrderForm();
		orderForm.getOrder().initOrder(account, cart);
		model.addAttribute("orderForm", orderForm);
		return page(PAYMENT, model);
	}

	@RequestMapping(value = "/shop/newOrder.do", method = RequestMethod.POST, params = "!_finish")
	public String nextPage(@ModelAttribute("orderForm") OrderForm orderForm, BindingResult result,
			@RequestParam(required = false) String shippingAddressRequired, Model model) {
		int current = orderForm.currentPage();
		if (current == PAYMENT && shippingAddressRequired == null) {
			orderForm.setShippingAddressRequired(false);
		}
		int target = current == PAYMENT && orderForm.isShippingAddressRequired() ? SHIPPING : CONFIRMATION;
		if (target > current) {
			validatePage(orderForm.getOrder(), result, current);
			if (result.hasErrors()) {
				return page(current, model);
			}
		}
		orderForm.moveTo(target);
		return page(target, model);
	}

	@RequestMapping(value = "/shop/newOrder.do", params = "_finish")
	public String finish(@ModelAttribute("orderForm") OrderForm orderForm, BindingResult result,
			SessionStatus status, HttpSession session, Model model) {
		for (int page = PAYMENT; page < PAGES.length; page++) {
			validatePage(orderForm.getOrder(), result, page);
			if (result.hasErrors()) {
				orderForm.moveTo(page);
				return page(page, model);
			}
		}
		petStore.insertOrder(orderForm.getOrder());
		session.removeAttribute(ViewSupport.CART);
		status.setComplete();
		model.addAttribute("order", orderForm.getOrder());
		model.addAttribute("message", "Thank you, your order has been submitted.");
		return "ViewOrder";
	}

	@RequestMapping("/shop/listOrders.do")
	public String listOrders(HttpSession session, Model model) {
		String username = ViewSupport.userSession(session).getAccount().getUsername();
		model.addAttribute("orderList", petStore.getOrdersByUsername(username));
		return "ListOrders";
	}

	@RequestMapping("/shop/viewOrder.do")
	public String viewOrder(@RequestParam int orderId, HttpSession session, Model model) {
		Order order = petStore.getOrder(orderId);
		if (!ViewSupport.userSession(session).getAccount().getUsername().equals(order.getUsername())) {
			return ViewSupport.error(model, "You may only view your own orders.");
		}
		model.addAttribute("order", order);
		return "ViewOrder";
	}

	private void validatePage(Order order, Errors errors, int page) {
		errors.pushNestedPath("order");
		if (page == PAYMENT) {
			orderValidator.validateCreditCard(order, errors);
			orderValidator.validateBillingAddress(order, errors);
		}
		else if (page == SHIPPING) {
			orderValidator.validateShippingAddress(order, errors);
		}
		errors.popNestedPath();
	}

	private String page(int page, Model model) {
		if (page == PAYMENT) {
			model.addAttribute("creditCardTypes", CREDIT_CARD_TYPES);
		}
		return PAGES[page];
	}
}
