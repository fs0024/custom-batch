package com.trinet.bnftnwbandbatchprocessor.conf;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Log4j2
@Configuration
public class BatchConfiguration {

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(12);
		executor.setCorePoolSize(10);
		executor.setQueueCapacity(15);
		executor.setThreadNamePrefix("Worker-");
		return executor;
	}

	@Bean
	public JobLauncher asyncJobLauncher(@Qualifier("jobRepository") JobRepository jobRepository){
	
			SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
			jobLauncher.setJobRepository(jobRepository);
			jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
			log.info("Launcher created successfully");
			return jobLauncher;
	}

	@Bean
	public JobExplorer jobExplorer(@Qualifier("batchJobDataSource")DataSource dataSource) throws Exception {
		JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
		jobExplorerFactoryBean.setDataSource(dataSource);
		return jobExplorerFactoryBean.getObject();
	}

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(@Qualifier("jobRegistry")JobRegistry jobRegistry) {
		JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
		postProcessor.setJobRegistry(jobRegistry);
		return postProcessor;
	}

	@Bean
	public JobLauncherTestUtils initJobLauncherTestUtils() {
		return new JobLauncherTestUtils() {
			@Override
			@Autowired
			public void setJob(@Qualifier("rateBandJobInit") Job job) {
				super.setJob(job);
			}

			@Override
			@Autowired
			public void setJobLauncher(@Qualifier("jobLauncher") JobLauncher launcher) {
				super.setJobLauncher(launcher);
			}

			@Override
			@Autowired
			public void setJobRepository(@Qualifier("jobRepository") JobRepository repository) {
				super.setJobRepository(repository);
			}

		};

	}

	@Bean
	public JobLauncherTestUtils jobLauncherTestUtils() {
		return new JobLauncherTestUtils() {
			@Override
			@Autowired
			public void setJob(@Qualifier("rateBandJob") Job job) {
				super.setJob(job);
			}

			@Override
			@Autowired
			public void setJobRepository(@Qualifier("jobRepository") JobRepository repository) {
				super.setJobRepository(repository);
			}

			@Override
			@Autowired
			public void setJobLauncher(@Qualifier("jobLauncher") JobLauncher launcher) {
				super.setJobLauncher(launcher);
			}
		};

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}

}
