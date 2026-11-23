package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.OrderDao;
import org.springframework.samples.jpetstore.domain.LineItem;
import org.springframework.samples.jpetstore.domain.Order;

public class MyBatisOrderDao implements OrderDao {

	private final SqlSession sqlSession;

	private final MyBatisSequenceDao sequenceDao;

	public MyBatisOrderDao(SqlSession sqlSession, MyBatisSequenceDao sequenceDao) {
		this.sqlSession = sqlSession;
		this.sequenceDao = sequenceDao;
	}

	public List getOrdersByUsername(String username) {
		return sqlSession.selectList("Order.getOrdersByUsername", username);
	}

	public Order getOrder(int orderId) {
		Order order = sqlSession.selectOne("Order.getOrder", orderId);
		if (order != null) {
			order.setLineItems(sqlSession.selectList("LineItem.getLineItemsByOrderId", order.getOrderId()));
		}
		return order;
	}

	public void insertOrder(Order order) {
		order.setOrderId(sequenceDao.getNextId("ordernum"));
		sqlSession.insert("Order.insertOrder", order);
		sqlSession.insert("Order.insertOrderStatus", order);
		for (Object element : order.getLineItems()) {
			LineItem lineItem = (LineItem) element;
			lineItem.setOrderId(order.getOrderId());
			sqlSession.insert("LineItem.insertLineItem", lineItem);
		}
	}
}
