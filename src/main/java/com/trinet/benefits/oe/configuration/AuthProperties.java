/**
 * 
 */
package com.trinet.benefits.oe.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author imistry1
 *
 * 
 */
@Configuration
@ConfigurationProperties
public class AuthProperties {
	
	@Value("${microservice.url}")
	private String schedulerBaseUrl;

	@Value("${platform.auth.url}")
	private String authUrl;

	//@Value("${openam.job.scheduler.user}")
	@Value("${api.batch.login.user}")
	private String schedulerUserName;

	//@Value("${openam.job.scheduler.password}")
	@Value("${api.batch.login.password}")
	private String schedulerPassword;

	@Value("${auth.cookie}")
	private String authCookieName;
	
	public String getSchedulerBaseUrl() {
		return schedulerBaseUrl;
	}

	public void setSchedulerBaseUrl(String schedulerBaseUrl) {
		this.schedulerBaseUrl = schedulerBaseUrl;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getSchedulerUserName() {
		return schedulerUserName;
	}

	public void setSchedulerUserName(String schedulerUserName) {
		this.schedulerUserName = schedulerUserName;
	}

	public String getSchedulerPassword() {
		return schedulerPassword;
	}

	public void setSchedulerPassword(String schedulerPassword) {
		this.schedulerPassword = schedulerPassword;
	}

	public String getAuthCookieName() {
		return authCookieName;
	}

	public void setAuthCookieName(String authCookieName) {
		this.authCookieName = authCookieName;
	}

}
