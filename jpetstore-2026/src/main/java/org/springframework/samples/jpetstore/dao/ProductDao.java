package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.samples.jpetstore.model.Product;

public interface ProductDao {

	List<Product> getProductListByCategory(String categoryId);

	List<Product> searchProductList(String keywords);

	Product getProduct(String productId);
}
