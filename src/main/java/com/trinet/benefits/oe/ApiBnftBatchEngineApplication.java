package com.trinet.benefits.oe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan({ 
"com.trinet.benefits.oe",
"com.trinet.benefits.oe.configuration",
"com.trinet.common", 
"com.trinet.redis", 
"com.trinet.security" 
})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, JmxAutoConfiguration.class, RedisAutoConfiguration.class })
@PropertySource("classpath:api-bnft-batch-engine.properties")
@PropertySource(value = "file:${MICROSERVICES_CONFIG}/api-bnft-batch-engine.properties", ignoreResourceNotFound = true)
public class ApiBnftBatchEngineApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApiBnftBatchEngineApplication.class, args);
	}

}
