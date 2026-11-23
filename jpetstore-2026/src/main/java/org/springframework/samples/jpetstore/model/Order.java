package org.springframework.samples.jpetstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order implements Serializable {

	private int orderId;

	private String username;

	private Date orderDate;

	private String shipAddress1;

	private String shipAddress2;

	private String shipCity;

	private String shipState;

	private String shipZip;

	private String shipCountry;

	private String billAddress1;

	private String billAddress2;

	private String billCity;

	private String billState;

	private String billZip;

	private String billCountry;

	private String courier;

	private double totalPrice;

	private String billToFirstName;

	private String billToLastName;

	private String shipToFirstName;

	private String shipToLastName;

	private String creditCard;

	private String expiryDate;

	private String cardType;

	private String locale;

	private String status;

	private List<LineItem> lineItems = new ArrayList<>();

	public void initOrder(Account account, Cart cart) {
		username = account.getUsername();
		orderDate = new Date();
		shipTo(account);
		billTo(account);
		totalPrice = cart.getSubTotal();
		useDefaultPaymentAndDelivery();
		for (Iterator<CartItem> cartItems = cart.getAllCartItems(); cartItems.hasNext();) {
			addLineItem(cartItems.next());
		}
	}

	public void addLineItem(CartItem cartItem) {
		addLineItem(new LineItem(lineItems.size() + 1, cartItem));
	}

	public void addLineItem(LineItem lineItem) {
		lineItems.add(lineItem);
	}

	private void shipTo(Account account) {
		shipToFirstName = account.getFirstName();
		shipToLastName = account.getLastName();
		shipAddress1 = account.getAddress1();
		shipAddress2 = account.getAddress2();
		shipCity = account.getCity();
		shipState = account.getState();
		shipZip = account.getZip();
		shipCountry = account.getCountry();
	}

	private void billTo(Account account) {
		billToFirstName = account.getFirstName();
		billToLastName = account.getLastName();
		billAddress1 = account.getAddress1();
		billAddress2 = account.getAddress2();
		billCity = account.getCity();
		billState = account.getState();
		billZip = account.getZip();
		billCountry = account.getCountry();
	}

	private void useDefaultPaymentAndDelivery() {
		creditCard = "999 9999 9999 9999";
		expiryDate = "12/03";
		cardType = "Visa";
		courier = "UPS";
		locale = "CA";
		status = "P";
	}
}
