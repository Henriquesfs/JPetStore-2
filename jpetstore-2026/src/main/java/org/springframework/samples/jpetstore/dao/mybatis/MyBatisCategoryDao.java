package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.CategoryDao;
import org.springframework.samples.jpetstore.model.Category;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisCategoryDao implements CategoryDao {

	private final SqlSession sqlSession;

	@Override
	public List<Category> getCategoryList() {
		return sqlSession.selectList("Category.getCategoryList");
	}

	@Override
	public Category getCategory(String categoryId) {
		return sqlSession.selectOne("Category.getCategory", categoryId);
	}
}
