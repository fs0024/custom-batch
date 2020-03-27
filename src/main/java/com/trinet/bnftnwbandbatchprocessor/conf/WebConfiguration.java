/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.conf;

/**
 * @author imistry
 *
 */
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.trinet.bnftnwbandbatchprocessor.interceptor.SecurityInterceptor;





@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * The validation constraint uses the validator.
     */
    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setProviderClass(HibernateValidator.class);
        return factory;
    }

    @Bean
    public SecurityInterceptor batchEngineRequestInterceptor() {
        return new SecurityInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(batchEngineRequestInterceptor()).addPathPatterns("/**");
    }

}

