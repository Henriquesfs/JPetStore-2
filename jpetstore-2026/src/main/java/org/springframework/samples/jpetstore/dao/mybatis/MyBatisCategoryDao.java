package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.CategoryDao;
import org.springframework.samples.jpetstore.domain.Category;

public class MyBatisCategoryDao implements CategoryDao {

	private final SqlSession sqlSession;

	public MyBatisCategoryDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List getCategoryList() {
		return sqlSession.selectList("Category.getCategoryList");
	}

	public Category getCategory(String categoryId) {
		return sqlSession.selectOne("Category.getCategory", categoryId);
	}
}
