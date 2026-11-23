package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.ProductDao;
import org.springframework.samples.jpetstore.domain.Product;

public class MyBatisProductDao implements ProductDao {

	private final SqlSession sqlSession;

	public MyBatisProductDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List getProductListByCategory(String categoryId) {
		return sqlSession.selectList("Product.getProductListByCategory", categoryId);
	}

	public Product getProduct(String productId) {
		return sqlSession.selectOne("Product.getProduct", productId);
	}

	public List searchProductList(String keywords) {
		return sqlSession.selectList("Product.searchProductList", new ProductSearch(keywords));
	}

	public static class ProductSearch {

		private final List<String> keywordList = new ArrayList<String>();

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
