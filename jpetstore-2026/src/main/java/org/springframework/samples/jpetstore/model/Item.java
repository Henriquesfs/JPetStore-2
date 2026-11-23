package org.springframework.samples.jpetstore.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item implements Serializable {

	private String itemId;

	private String productId;

	private double listPrice;

	private double unitCost;

	private int supplierId;

	private String status;

	private String attribute1;

	private String attribute2;

	private String attribute3;

	private String attribute4;

	private String attribute5;

	private Product product;

	private int quantity;

	public void setItemId(String itemId) { this.itemId = itemId.trim(); }

	@Override
	public String toString() {
		return "(" + getItemId().trim() + "-" + getProductId().trim() + ")";
	}
}
