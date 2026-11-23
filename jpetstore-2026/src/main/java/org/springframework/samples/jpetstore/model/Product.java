package org.springframework.samples.jpetstore.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product implements Serializable {

	private String productId;

	private String categoryId;

	private String name;

	private String description;

	public void setProductId(String productId) { this.productId = productId.trim(); }

	@Override
	public String toString() {
		return getName();
	}
}
