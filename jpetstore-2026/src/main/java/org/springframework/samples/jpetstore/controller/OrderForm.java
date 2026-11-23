package org.springframework.samples.jpetstore.controller;

import java.io.Serializable;

import org.springframework.samples.jpetstore.model.Order;

public class OrderForm implements Serializable {

	private final Order order = new Order();

	private boolean shippingAddressRequired;

	private int page;

	public Order getOrder() {
		return order;
	}

	public void setShippingAddressRequired(boolean shippingAddressRequired) {
		this.shippingAddressRequired = shippingAddressRequired;
	}

	public boolean isShippingAddressRequired() {
		return shippingAddressRequired;
	}

	int currentPage() {
		return page;
	}

	void moveTo(int page) {
		this.page = page;
	}
}
