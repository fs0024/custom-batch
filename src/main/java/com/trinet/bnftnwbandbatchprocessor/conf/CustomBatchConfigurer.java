package com.trinet.bnftnwbandbatchprocessor.conf;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class CustomBatchConfigurer extends DefaultBatchConfigurer {

    private static final Logger LOGGER = LogManager.getLogger(CustomBatchConfigurer.class);

    @Autowired
    @Qualifier("batchJobDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("batchTransactionManager")
    private PlatformTransactionManager transactionManager;


    @Override
    public JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        // use the autowired data source
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        factory.setTablePrefix("BATCH_");
        try {
            factory.afterPropertiesSet();
            JobRepository repository =  factory.getObject() ;
            LOGGER.info("Custom JobRepository successfully initialized");
            return repository ;
        } catch (Exception e) {
            LOGGER.error("Custom JobRepository bean could not be initialized");
            throw new BatchConfigurationException(e);
        }
    }



    @Override
    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

}
