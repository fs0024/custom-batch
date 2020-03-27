package com.trinet.bnftnwbandbatchprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({
		"com.trinet.bnftnwbandbatchprocessor",
		"com.trinet.common", 
		"com.trinet.redis", 
		"com.trinet.security" 
})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, JmxAutoConfiguration.class, RedisAutoConfiguration.class })
@PropertySource("classpath:api-bnft-nwband-bpc.properties")
@PropertySource(value = "file:${MICROSERVICES_CONFIG}/api-bnft-nwband-bpc.properties", ignoreResourceNotFound = true)
public class BnftNwbandBatchProcessorApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BnftNwbandBatchProcessorApplication.class, args);
	}

}
