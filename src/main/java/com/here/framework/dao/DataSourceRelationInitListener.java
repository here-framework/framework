package com.here.framework.dao;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.here.framework.init.BeanInitListener;
/**
 * 设置数据源时候调用
 * @author koujp
 *
 */
public class DataSourceRelationInitListener implements BeanInitListener,ApplicationContextAware{
	private ApplicationContext context;
	private DataSource datasource=null;
	@Override
	public boolean isEnable(Object bean, Object beanName) {
		return true;
	}

	@Override
	public Object beforeBeanCreate(Object bean, Object beanName) {
		if(null==datasource){
			initDatasource();
		}
		if(bean instanceof SqlSessionFactoryBean){
			SqlSessionFactoryBean sqlSessionFactoryBean=(SqlSessionFactoryBean)bean;
			sqlSessionFactoryBean.setDataSource(datasource);
		}
		if(bean instanceof DataSourceTransactionManager){
			DataSourceTransactionManager transactionManager=(DataSourceTransactionManager)bean;
			transactionManager.setDataSource(datasource);
		}
		return bean;
	}
	private void initDatasource(){
		
		Map<String,DataSource> datasources=context.getBeansOfType(DataSource.class);
		if(null!=datasources){
			Collection<DataSource> values=datasources.values();
			if(null!=values && values.size()>0){
				datasource=values.toArray(new DataSource[0])[0];
			}
		}
	}
	@Override
	public Object afterBeanCreate(Object bean, Object beanName) {
		return bean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		context=applicationcontext;
	}
}
