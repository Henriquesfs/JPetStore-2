package org.springframework.samples.jpetstore.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category implements Serializable {

	private String categoryId;

	private String name;

	private String description;

	public void setCategoryId(String categoryId) { this.categoryId = categoryId.trim(); }

	@Override
	public String toString() {
		return getCategoryId();
	}
}
