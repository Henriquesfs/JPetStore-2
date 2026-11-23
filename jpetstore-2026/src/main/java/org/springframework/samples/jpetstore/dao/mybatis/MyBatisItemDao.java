package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.ItemDao;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.LineItem;
import org.springframework.samples.jpetstore.domain.Order;

public class MyBatisItemDao implements ItemDao {

	private final SqlSession sqlSession;

	public MyBatisItemDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void updateQuantity(Order order) {
		for (Object element : order.getLineItems()) {
			LineItem lineItem = (LineItem) element;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("itemId", lineItem.getItemId());
			parameters.put("increment", lineItem.getQuantity());
			sqlSession.update("Item.updateInventoryQuantity", parameters);
		}
	}

	public boolean isItemInStock(String itemId) {
		Integer quantity = sqlSession.selectOne("Item.getInventoryQuantity", itemId);
		return quantity != null && quantity > 0;
	}

	public List getItemListByProduct(String productId) {
		return sqlSession.selectList("Item.getItemListByProduct", productId);
	}

	public Item getItem(String itemId) {
		Item item = sqlSession.selectOne("Item.getItem", itemId);
		if (item != null) {
			Integer quantity = sqlSession.selectOne("Item.getInventoryQuantity", itemId);
			item.setQuantity(quantity);
		}
		return item;
	}
}
