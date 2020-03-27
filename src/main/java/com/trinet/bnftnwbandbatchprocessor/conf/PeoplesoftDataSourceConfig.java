package com.trinet.bnftnwbandbatchprocessor.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories (
		entityManagerFactoryRef = "psEntityManager", 
		transactionManagerRef = "psTransactionManager", 
		basePackages = "com.trinet.bnftnwbandbatchprocessor.conf.repository.peoplesoft"
)
public class PeoplesoftDataSourceConfig {

	private static final Logger LOGGER = LogManager.getLogger(PeoplesoftDataSourceConfig.class);
	
	@Bean(name="psDataSource")
    public DataSource dataSource(@Value( "${peoplesoft.jndi-name}" ) String jndiName) {
    	DataSource dataSource = new JndiDataSourceLookup().getDataSource(jndiName);
    	
    	LOGGER.debug(() -> "Configured peoplesoft datasource");
    	return dataSource;
    }
    
    @Bean(name="psEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("psDataSource") DataSource dataSource) {
		
    	LOGGER.debug(() -> "Configuring peoplesoft entity manager");
    	
		return builder
				.dataSource(dataSource)
				.packages("com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft")
				.persistenceUnit("psPersistenceUnit")
				.build();
	}
	
    @Bean(name="psTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("psEntityManager") EntityManagerFactory emf) {
    	
    	LOGGER.debug(() -> "Configuring peoplesoft transaction manager");
        return new JpaTransactionManager(emf);
    }
	
}
