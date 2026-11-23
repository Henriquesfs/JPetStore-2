package org.springframework.samples.jpetstore.dao.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisSequenceDao {

	private final SqlSession sqlSession;

	public int getNextId(String name) {
		Sequence sequence = sqlSession.selectOne("Sequence.getSequence", new Sequence(name, -1));
		if (sequence == null) {
			throw new DataRetrievalFailureException(
					"Could not get next value of sequence '" + name + "': sequence does not exist");
		}
		sqlSession.update("Sequence.updateSequence", new Sequence(name, sequence.getNextId() + 1));
		return sequence.getNextId();
	}
}
