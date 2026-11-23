package org.springframework.samples.jpetstore.dao.mybatis;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.samples.jpetstore.dao.ItemDao;
import org.springframework.samples.jpetstore.model.Item;
import org.springframework.samples.jpetstore.model.LineItem;
import org.springframework.samples.jpetstore.model.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisItemDao implements ItemDao {

	private final SqlSession sqlSession;

	@Override
	public void updateQuantity(Order order) {
		for (LineItem lineItem : order.getLineItems()) {
			sqlSession.update("Item.updateInventoryQuantity",
					Map.of("itemId", lineItem.getItemId(), "increment", lineItem.getQuantity()));
		}
	}

	@Override
	public boolean isItemInStock(String itemId) {
		Integer quantity = sqlSession.selectOne("Item.getInventoryQuantity", itemId);
		return quantity != null && quantity > 0;
	}

	@Override
	public List<Item> getItemListByProduct(String productId) {
		return sqlSession.selectList("Item.getItemListByProduct", productId);
	}

	@Override
	public Item getItem(String itemId) {
		return sqlSession.selectOne("Item.getItem", itemId);
	}
}
