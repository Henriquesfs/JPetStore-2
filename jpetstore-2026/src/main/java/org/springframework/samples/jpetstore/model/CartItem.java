package org.springframework.samples.jpetstore.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem implements Serializable {

	private Item item;

	private int quantity;

	private boolean inStock;

	public double getTotalPrice() {
		return item != null ? item.getListPrice() * quantity : 0;
	}

	public void incrementQuantity() {
		quantity++;
	}
}
