package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages="com.example.demo.sample_dev", sqlSessionFactoryRef="sqlSessionFactoryDev")
public class DatabaseConfigDev {
	
	@Autowired
	private ApplicationContext applicationContext;


	@Bean(name="hikariConfigDev")
	@ConfigurationProperties(prefix = "spring.datasource.mssqldev")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean(name="dataSourceDev")
	@Qualifier("dataSourceDev")
	public DataSource dataSource(@Qualifier("hikariConfigDev") HikariConfig hikariConfig) {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
		return new HikariDataSource(hikariConfig);
		
	}

	
	@Bean(name="sqlSessionFactoryDev")
	@Qualifier("sqlSessionFactoryDev")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceDev") DataSource dataSource) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*MapperDev.xml"));
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="sqlSessionTemplateDev")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryDev") SqlSessionFactory sqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	
	@Bean(name="transactionManagerDev")
	@Qualifier("transactionManagerDev")
	public PlatformTransactionManager transactionManager(@Qualifier("dataSourceDev") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name="chainedTransactionManager")
	@Qualifier("chainedTransactionManager")
	public PlatformTransactionManager chainedTransactionManager(
			@Qualifier("transactionManager") PlatformTransactionManager transactionManager,
			@Qualifier("transactionManagerDev") PlatformTransactionManager transactionManagerDev) {
		return new ChainedTransactionManager(transactionManager, transactionManagerDev);
	}
	
}
