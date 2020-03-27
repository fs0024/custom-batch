package com.trinet.bnftnwbandbatchprocessor.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories (
		entityManagerFactoryRef = "batchJobEntityManager", 
		transactionManagerRef = "batchTransactionManager",
		basePackages = "com.trinet.bnftnwbandbatchprocessor.repository.batch"
)
@EnableTransactionManagement
public class BatchJobDataSourceConfig {

	private static final Logger LOGGER = LogManager.getLogger(BatchJobDataSourceConfig.class);
	
	@Primary
	@Bean(name="batchJobDataSource")
    public DataSource dataSource(@Value( "${api-bnft-batch-engine.jndi-name}" ) String jndiName) {
    	DataSource dataSource = new JndiDataSourceLookup().getDataSource(jndiName);
    	
    	LOGGER.debug(() -> "Configured batch job datasource");
    	return dataSource;
    }
    
	@Primary
    @Bean(name="batchJobEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("batchJobDataSource") DataSource dataSource) {
		
		return builder
				.dataSource(dataSource)
				.packages("com.trinet.bnftnwbandbatchprocessor.entity.batch")
				.persistenceUnit("batchJobPersistenceUnit")
				.build();
	}
	
	@Primary
    @Bean(name="batchTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("batchJobEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
	
}

