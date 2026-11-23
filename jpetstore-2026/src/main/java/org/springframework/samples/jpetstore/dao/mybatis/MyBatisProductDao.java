package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.ProductDao;
import org.springframework.samples.jpetstore.model.Product;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisProductDao implements ProductDao {

	private final SqlSession sqlSession;

	@Override
	public List<Product> getProductListByCategory(String categoryId) {
		return sqlSession.selectList("Product.getProductListByCategory", categoryId);
	}

	@Override
	public Product getProduct(String productId) {
		return sqlSession.selectOne("Product.getProduct", productId);
	}

	@Override
	public List<Product> searchProductList(String keywords) {
		return sqlSession.selectList("Product.searchProductList", new ProductSearch(keywords));
	}

	public static class ProductSearch {

		private final List<String> keywordList = new ArrayList<>();

		public ProductSearch(String keywords) {
			StringTokenizer splitter = new StringTokenizer(keywords, " ", false);
			while (splitter.hasMoreTokens()) {
				keywordList.add("%" + splitter.nextToken() + "%");
			}
		}

		public List<String> getKeywordList() {
			return keywordList;
		}
	}
}
