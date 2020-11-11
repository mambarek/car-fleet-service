package com.it2go.micro.carfleetservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * created by mmbarek on 10.11.2020.
 */
//@Configuration
//@EnableTransactionManagement
public class PersistenceJPAConfig {
 // @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
    //em.setDataSource(dataSource());
    em.setPackagesToScan(new String[] { "com.it2go.micro.carfleetservice.persistence.jpa.entities",
    "com.it2go.util.jpa.entities"});

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    /*em.setJpaProperties(additionalProperties());*/

    return em;
  }
}
