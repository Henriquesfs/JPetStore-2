package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.samples.jpetstore.model.Category;

public interface CategoryDao {

	List<Category> getCategoryList();

	Category getCategory(String categoryId);
}
