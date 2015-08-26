package com.here.framework.dao;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.Assert;
/**
 * mapper sql session
 * @author koujp
 *
 */
public class CustomSqlSession{
	private SqlSession sqlSession;
	public CustomSqlSession(SqlSession sqlSession){
		this.sqlSession=sqlSession;
	}
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	public  <T> T getMapper(Class<T> clazz){
		checkMapperConfiguration(clazz);
		return sqlSession.getMapper(clazz);
	}
	private synchronized <T> void checkMapperConfiguration(Class<T> clazz){
		Assert.notNull(sqlSession, "Property 'sqlSession' is required");
		Configuration config=sqlSession.getConfiguration();
		Assert.notNull(clazz, "Property 'mapper clazz' is required");
		if(!config.hasMapper(clazz)){
			config.addMapper(clazz);
		}
	}
}
