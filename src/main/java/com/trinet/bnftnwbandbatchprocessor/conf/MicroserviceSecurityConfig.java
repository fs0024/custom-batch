/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.conf;

import com.trinet.security.TriNetSecurityFilter;
import com.trinet.security.configuration.BasicSecurityConfig;
import com.trinet.security.filters.InvalidResourceFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * This configuration implements full authentication security.
 * The main spring configuration resides in the securityContext.xml
 * in the api-security project.
 */
@Configuration
public class MicroserviceSecurityConfig extends BasicSecurityConfig {
	
	@Value("${management.endpoints.web.base-path}")
	private String actuatorBasePath;

	
	
    @Bean
    public FilterRegistrationBean<InvalidResourceFilter> invalidResourceFilterRegistration(InvalidResourceFilter invalidResourceFilter) {
        FilterRegistrationBean<InvalidResourceFilter> registration = new FilterRegistrationBean<>(invalidResourceFilter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<TriNetSecurityFilter> triNetSecurityFilterRegistration(TriNetSecurityFilter triNetSecurityFilter) {
        FilterRegistrationBean<TriNetSecurityFilter> registration = new FilterRegistrationBean<>(triNetSecurityFilter);
        registration.setEnabled(false);
        return registration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // must call super first
        super.configure(http);
        http.authorizeRequests()
        .antMatchers("/v1/**").access("hasRole('TMT')|| @authorizationChecks.isSystemAccount(authentication, null)")
        .and()
        .csrf().disable();
      
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if(true) {
            super.configure(web);
            web.ignoring().antMatchers(actuatorBasePath + "/health").antMatchers("/v1/**");
        }
    }
	
}

