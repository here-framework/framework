package com.here.framework.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.here.framework.log.HLogger;
/**
 * 数据源
 * @author koujp
 *
 */
public class JtaDataSource extends AtomikosNonXADataSourceBean implements DataSource{
	private static final long serialVersionUID = 4030891368340391930L;
	public JtaDataSource(DatasourceConfig dataSouceConfig){
		this.initConfig(dataSouceConfig);
	}
	private void initConfig(DatasourceConfig dataSouceConfig){
		this.setUser(dataSouceConfig.getUsername());
		this.setPassword(dataSouceConfig.getPassword());
		this.setUrl(dataSouceConfig.getUrl());
		this.setDriverClassName(dataSouceConfig.getDriverClassName());
		this.setPoolSize(dataSouceConfig.getPoolSize());
		
		try {
			this.setLoginTimeout(dataSouceConfig.getTimeout());
		} catch (SQLException e) {
			HLogger.getInstance(this.getClass()).error(e);
		}
	}
}
