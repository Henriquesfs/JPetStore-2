package org.springframework.samples.jpetstore.web;

import java.util.Iterator;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.jpetstore.domain.Cart;
import org.springframework.samples.jpetstore.domain.CartItem;
import org.springframework.samples.jpetstore.domain.logic.PetStoreFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

	private final PetStoreFacade petStore;

	@Autowired
	public CartController(PetStoreFacade petStore) {
		this.petStore = petStore;
	}

	@RequestMapping("/shop/addItemToCart.do")
	public String addItemToCart(@RequestParam String workingItemId, HttpSession session, Model model) {
		Cart cart = cart(session);
		if (cart.containsItemId(workingItemId)) {
			cart.incrementQuantityByItemId(workingItemId);
		}
		else {
			boolean isInStock = petStore.isItemInStock(workingItemId);
			cart.addItem(petStore.getItem(workingItemId), isInStock);
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
		for (Iterator cartItems = cart.getAllCartItems(); cartItems.hasNext();) {
			CartItem cartItem = (CartItem) cartItems.next();
			String itemId = cartItem.getItem().getItemId();
			try {
				int quantity = Integer.parseInt(quantities.get(itemId));
				cart.setQuantityByItemId(itemId, quantity);
				if (quantity < 1) {
					cartItems.remove();
				}
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
