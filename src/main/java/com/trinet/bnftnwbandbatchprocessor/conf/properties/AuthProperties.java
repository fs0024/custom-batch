/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.conf.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author imistry1
 *
 * 
 */
@Configuration
@Getter
@Setter
@lombok.Generated
public class AuthProperties {
	
	@Value("${microservice.url}")
	private String schedulerBaseUrl;

	@Value("${platform.auth.url}")
	private String authUrl;

	
	@Value("${api.batch.login.user}")
	private String schedulerUserName;

	
	@Value("${api.batch.login.password}")
	private String schedulerPassword;

	@Value("${auth.cookie}")
	private String authCookieName;

}
