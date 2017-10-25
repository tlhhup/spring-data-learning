package com.woniuxy.springdata;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableJpaRepositories(basePackages="com.woniuxy.springdata.repository")
@EnableTransactionManagement
public class App {
	
	@Value("${jdbc.user}")
	private String userName;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Value("${jdbc.url}")
	private String url;
	
	@Value("${jdbc.driver}")
	private String driverClass;
	
	//1.���������ļ�
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}

	//2.��������Դ
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	//3.����ʵ�������bean
	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory=new LocalContainerEntityManagerFactoryBean();
		//1.��������Դ
		entityManagerFactory.setDataSource(dataSource());
		//2.����ɨ���ʵ����İ�
		entityManagerFactory.setPackagesToScan("com.woniuxy.springdata.entity");
		//3.ָ��jpaʵ����
		HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		
		return entityManagerFactory;
	}
	
	//4.�������������
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager transactionManager=new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}
	
}
