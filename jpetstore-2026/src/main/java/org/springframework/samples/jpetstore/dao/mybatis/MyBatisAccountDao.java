package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.domain.Account;

public class MyBatisAccountDao implements AccountDao {

	private final SqlSession sqlSession;

	public MyBatisAccountDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Account getAccount(String username) {
		return sqlSession.selectOne("Account.getAccountByUsername", username);
	}

	public Account getAccount(String username, String password) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		return sqlSession.selectOne("Account.getAccountByUsernameAndPassword", account);
	}

	public void insertAccount(Account account) {
		sqlSession.insert("Account.insertAccount", account);
		sqlSession.insert("Account.insertProfile", account);
		sqlSession.insert("Account.insertSignon", account);
	}

	public void updateAccount(Account account) {
		sqlSession.update("Account.updateAccount", account);
		sqlSession.update("Account.updateProfile", account);
		if (account.getPassword() != null && account.getPassword().length() > 0) {
			sqlSession.update("Account.updateSignon", account);
		}
	}

	public List getUsernameList() {
		return sqlSession.selectList("Account.getUsernameList");
	}
}
