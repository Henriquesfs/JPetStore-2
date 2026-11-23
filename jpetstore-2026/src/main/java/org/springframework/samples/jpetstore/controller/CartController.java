package org.springframework.samples.jpetstore.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.samples.jpetstore.model.Cart;
import org.springframework.samples.jpetstore.model.CartItem;
import org.springframework.samples.jpetstore.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final CatalogService catalog;

	@RequestMapping("/shop/addItemToCart.do")
	public String addItemToCart(@RequestParam String workingItemId, HttpSession session, Model model) {
		Cart cart = cart(session);
		if (cart.containsItemId(workingItemId)) {
			cart.incrementQuantityByItemId(workingItemId);
		}
		else {
			boolean isInStock = catalog.isItemInStock(workingItemId);
			cart.addItem(catalog.getItem(workingItemId), isInStock);
		}
		return show("Cart", cart, model);
	}

	@RequestMapping("/shop/removeItemFromCart.do")
	public String removeItemFromCart(@RequestParam(required = false) String workingItemId, HttpSession session,
			Model model) {
		Cart cart = cart(session);
		cart.removeItemById(workingItemId);
		return show("Cart", cart, model);
	}

	@RequestMapping("/shop/updateCartQuantities.do")
	public String updateCartQuantities(@RequestParam Map<String, String> quantities, HttpSession session, Model model) {
		Cart cart = cart(session);
		for (CartItem cartItem : List.copyOf(cart.getCartItemList().getSource())) {
			String itemId = cartItem.getItem().getItemId();
			try {
				cart.updateQuantity(itemId, Integer.parseInt(quantities.get(itemId)));
			}
			catch (NumberFormatException ex) {
				continue;
			}
		}
		return show("Cart", cart, model);
	}

	@RequestMapping("/shop/viewCart.do")
	public String viewCart(@RequestParam(required = false) String page, HttpSession session, Model model) {
		return showPaged("Cart", page, session, model);
	}

	@RequestMapping("/shop/checkout.do")
	public String checkout(@RequestParam(required = false) String page, HttpSession session, Model model) {
		return showPaged("Checkout", page, session, model);
	}

	private String showPaged(String view, String page, HttpSession session, Model model) {
		UserSession userSession = ViewSupport.userSession(session);
		if (userSession != null) {
			ViewSupport.turnPage(userSession.getMyList(), page);
		}
		Cart cart = cart(session);
		ViewSupport.turnPage(cart.getCartItemList(), page, "nextCart", "previousCart");
		return show(view, cart, model);
	}

	private static String show(String view, Cart cart, Model model) {
		model.addAttribute("cart", cart);
		return view;
	}

	private static Cart cart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute(ViewSupport.CART);
		if (cart == null) {
			cart = new Cart();
			session.setAttribute(ViewSupport.CART, cart);
		}
		return cart;
	}
}
