package com.capitalone.demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.junit.Ignore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.capitalone.demo.entity.Account;
import com.capitalone.demo.repository.AccountRepository;;

@Ignore("not a test")
@Profile("postgresql")
@Configuration
@EnableJpaRepositories("com.capitalone.demo.repository")
public class PostgreRepositoryConfig implements RepositoryConfig {

  @Bean
  @Override
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://192.168.99.100:5432/capitalone");
    dataSource.setUsername("vista");
    dataSource.setPassword("vista");
    dataSource.setSchema("capitalone");
    return dataSource;
  }

  @Bean
  @Override
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPackagesToScan(Account.class.getPackage().getName(), AccountRepository.class.getPackage().getName());
    entityManagerFactoryBean.setJpaProperties(new Properties() {
      {
        put("spring.jpa.properties.hibernate.dialect", PostgreSQL82Dialect.class.getName());
        put("hibernate.current_session_context_class", SpringSessionContext.class.getName());
        put("spring.jpa.properties.hibernate.dialect", PostgreSQL82Dialect.class.getName());
        put("spring.jpa.properties.hibernate.default_schema", "capitalone");
        // put("spring.jpa.hibernate.ddl-auto", "create");
      }
    });
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {
      {
        setDatabase(Database.POSTGRESQL);
      }
    });
    return entityManagerFactoryBean;
  }

  @Bean
  @Override
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }
}