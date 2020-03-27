package com.trinet.benefits.oe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trinet.benefits.oe.common.Constants;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class BatchProcesserService {

    @Value("${api.base.url}")
    private String apiBaseUrl;
	
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BatchLoginService loginService;

	@SuppressWarnings("rawtypes")
	public String runNow(String batchName) {

		String token = loginService.login();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(Constants.TOKEN, token);

		HttpEntity entity = new HttpEntity<>(headers);
		
		
		String apiUrl = apiBaseUrl+"/"+batchName+"/v1/platform/runNow";

        log.debug(Constants.API_CONFIG_URL_MESSAGE + apiUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET,entity,String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.debug(Constants.API_RETURNED_CONFIG_MESSAGE + response);
               
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("Error invoking API : " + e.getMessage());
            throw e ;
        }

	}
	
	@SuppressWarnings("rawtypes")
	public String processorHealth(String batchName) {

		String token = loginService.login();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(Constants.TOKEN, token);

		HttpEntity entity = new HttpEntity<>(headers);
		
		
		String apiUrl = apiBaseUrl+"/"+batchName+"/v1/platform/monitor";
		

        log.debug(Constants.API_CONFIG_URL_MESSAGE+ apiUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET,entity,String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.debug(Constants.API_RETURNED_CONFIG_MESSAGE + response);
               
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("Error invoking API : " + e.getMessage());
            throw e ;
        }

	}
	
	
	@SuppressWarnings("rawtypes")
	public String updateSchedule(String batchName) {

		String token = loginService.login();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(Constants.TOKEN, token);

		HttpEntity entity = new HttpEntity<>(headers);
		
		
		String apiUrl = apiBaseUrl+"/"+batchName+"/v1/platform/updateSchedule";

        log.debug(Constants.API_CONFIG_URL_MESSAGE+ apiUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET,entity,String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.debug(Constants.API_RETURNED_CONFIG_MESSAGE + response);
               
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("Error invoking API : " + e.getMessage());
            throw e ;
        }

	}
	
	@SuppressWarnings("rawtypes")
	public String stopSchedule(String batchName) {

		String token = loginService.login();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(Constants.TOKEN, token);

		HttpEntity entity = new HttpEntity<>(headers);
		
		
		String apiUrl = apiBaseUrl+"/"+batchName+"/v1/platform/stopSchedule";

        log.debug(Constants.API_CONFIG_URL_MESSAGE+ apiUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET,entity,String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.debug(Constants.API_RETURNED_CONFIG_MESSAGE + response);
               
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("Error invoking API : " + e.getMessage());
            throw e ;
        }

	}
	
	
	@SuppressWarnings("rawtypes")
	public String stopJobs(String batchName) {

		String token = loginService.login();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(Constants.TOKEN, token);

		HttpEntity entity = new HttpEntity<>(headers);
		
		
		String apiUrl = apiBaseUrl+"/"+batchName+"/v1/platform/stopjobs";

        log.debug(Constants.API_CONFIG_URL_MESSAGE+ apiUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET,entity,String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.debug(Constants.API_RETURNED_CONFIG_MESSAGE + response);
               
            }

            return response.getBody();

        } catch (Exception e) {
            log.error("Error invoking API : " + e.getMessage());
            throw e ;
        }

	}
	

}
