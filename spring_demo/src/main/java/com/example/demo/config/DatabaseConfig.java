package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
	
	@Autowired
	private ApplicationContext applicationContext;


	@Bean(name="hikariConfig")
	@ConfigurationProperties(prefix = "spring.datasource.mssql")
	@Primary
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean(name="dataSource")
	@Qualifier("dataSource")
	@Primary
	public DataSource dataSource(@Qualifier("hikariConfig") HikariConfig hikariConfig) {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
		return new HikariDataSource(hikariConfig);
		
	}

	
	@Bean(name="sqlSessionFactory")
	@Qualifier("sqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*Mapper.xml"));
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="sqlSessionTemplate")
	@Qualifier("sqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	
	@Bean(name="transactionManager")
	@Qualifier("transactionManager")
	@Primary
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
//	@Bean(name="chainedTransactionManager")
//	@Qualifier("chainedTransactionManager")
//	public PlatformTransactionManager chainedTransactionManager() {
//		return new ChainedTransactionManager(transactionManager(), transactionManagerDev());
//	}
	
}
