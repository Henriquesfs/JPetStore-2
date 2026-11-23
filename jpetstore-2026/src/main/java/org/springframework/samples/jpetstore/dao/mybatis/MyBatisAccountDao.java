package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.model.Account;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisAccountDao implements AccountDao {

	private final SqlSession sqlSession;

	@Override
	public Account getAccount(String username) {
		return sqlSession.selectOne("Account.getAccountByUsername", username);
	}

	@Override
	public String getPassword(String username) {
		return sqlSession.selectOne("Account.getPassword", username);
	}

	@Override
	public void insertAccount(Account account, String encodedPassword) {
		sqlSession.insert("Account.insertAccount", account);
		sqlSession.insert("Account.insertProfile", account);
		sqlSession.insert("Account.insertSignon", signon(account, encodedPassword));
	}

	@Override
	public void updateAccount(Account account, String encodedPassword) {
		sqlSession.update("Account.updateAccount", account);
		sqlSession.update("Account.updateProfile", account);
		if (encodedPassword != null) {
			sqlSession.update("Account.updateSignon", signon(account, encodedPassword));
		}
	}

	@Override
	public List<String> getUsernameList() {
		return sqlSession.selectList("Account.getUsernameList");
	}

	private static Map<String, String> signon(Account account, String encodedPassword) {
		return Map.of("username", account.getUsername(), "password", encodedPassword);
	}
}
