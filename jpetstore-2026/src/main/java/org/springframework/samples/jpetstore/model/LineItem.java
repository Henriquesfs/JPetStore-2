package org.springframework.samples.jpetstore.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineItem implements Serializable {

	private int orderId;

	private int lineNumber;

	private int quantity;

	private String itemId;

	private double unitPrice;

	private Item item;

	public LineItem() {
	}

	public LineItem(int lineNumber, CartItem cartItem) {
		this.lineNumber = lineNumber;
		this.quantity = cartItem.getQuantity();
		this.itemId = cartItem.getItem().getItemId();
		this.unitPrice = cartItem.getItem().getListPrice();
		this.item = cartItem.getItem();
	}

	public double getTotalPrice() {
		return unitPrice * quantity;
	}
}
