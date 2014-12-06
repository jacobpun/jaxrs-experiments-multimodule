package org.punnoose.jersey.config;

import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableJpaRepositories(basePackages = { "org.punnoose.jersey.repository" })
@EnableTransactionManagement
@PropertySource("classpath:exercise-service.properties")
@ComponentScan(basePackages = { "org.punnoose.jersey.service"})
public class ServiceIntegrationTestConfiguration {
	@Bean
	public DozerBeanMapper getMapper() {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays
				.asList(new String[] { "dozer-bean-mappings.xml" }));
		return mapper;
	}
	

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.ORACLE);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("org.punnoose.jersey.model");

		Properties props = new Properties();
		props.put("hibernate.hbm2ddl.auto", "create");
		emfb.setJpaProperties(props);

		return emfb;
	}

	@Bean
	public DataSource dataSource(Environment env) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getProperty("jdbc.driver"));
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));
		return ds;
	}

	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setDataSource(dataSource);
		return manager;
	}
}