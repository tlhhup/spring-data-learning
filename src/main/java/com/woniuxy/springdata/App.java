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
	
	//1.分离属性文件
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}

	//2.配置数据源
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	//3.配置实体管理工厂bean
	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory=new LocalContainerEntityManagerFactoryBean();
		//1.设置数据源
		entityManagerFactory.setDataSource(dataSource());
		//2.设置扫描的实体类的包
		entityManagerFactory.setPackagesToScan("com.woniuxy.springdata.entity");
		//3.指定jpa实现商
		HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		
		return entityManagerFactory;
	}
	
	//4.配置事务管理器
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager transactionManager=new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}
	
}
