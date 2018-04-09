package com.capitalone.demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.capitalone.demo.repository")
public class PostgreRepositoryConfig implements RepositoryConfig {

  @Autowired
  Environment env;

  @Bean
  @Override
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));// "org.postgresql.Driver");
    dataSource.setUrl(env.getProperty("spring.datasource.url"));// "jdbc:postgresql://localhost:5432/capitalone");
    dataSource.setUsername(env.getProperty("spring.datasource.username"));// "vista");
    dataSource.setPassword(env.getProperty("spring.datasource.password"));// "vista");
    dataSource.setSchema(env.getProperty("spring.jpa.properties.hibernate.default_schema"));// "capitalone");
    // dataSource.setSchema("capitalone");
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
        put("hibernate.current_session_context_class", SpringSessionContext.class.getName());
        put("spring.jpa.properties.hibernate.dialect", PostgreSQL82Dialect.class.getName());
        put("spring.jpa.properties.hibernate.default_schema", env.getProperty("spring.jpa.properties.hibernate.default_schema"));// "capitalone");
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