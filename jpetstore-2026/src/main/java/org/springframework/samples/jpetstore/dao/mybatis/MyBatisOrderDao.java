package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.OrderDao;
import org.springframework.samples.jpetstore.model.LineItem;
import org.springframework.samples.jpetstore.model.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisOrderDao implements OrderDao {

	private final SqlSession sqlSession;

	private final MyBatisSequenceDao sequenceDao;

	@Override
	public List<Order> getOrdersByUsername(String username) {
		return sqlSession.selectList("Order.getOrdersByUsername", username);
	}

	@Override
	public Order getOrder(int orderId) {
		Order order = sqlSession.selectOne("Order.getOrder", orderId);
		if (order != null) {
			order.setLineItems(sqlSession.selectList("LineItem.getLineItemsByOrderId", order.getOrderId()));
		}
		return order;
	}

	@Override
	public void insertOrder(Order order) {
		order.setOrderId(sequenceDao.getNextId("ordernum"));
		sqlSession.insert("Order.insertOrder", order);
		sqlSession.insert("Order.insertOrderStatus", order);
		for (LineItem lineItem : order.getLineItems()) {
			lineItem.setOrderId(order.getOrderId());
			sqlSession.insert("LineItem.insertLineItem", lineItem);
		}
	}
}
