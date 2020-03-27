/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trinet.bnftnwbandbatchprocessor.conf.properties.AuthProperties;

import net.minidev.json.JSONObject;





/**
 * @author imistry1
 *
 * 
 */
@Service
public class BatchLoginService {
	
	@Autowired
	private AuthProperties authProperties;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	private String getAuthToken() {
		
		String authUrl = authProperties.getAuthUrl()+"/services/v1.0/authentication/signon?realm=sw_hrp";
		HttpHeaders headers = new HttpHeaders();
		JSONObject authJsonObject = new JSONObject();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		authJsonObject.put("guid", authProperties.getSchedulerUserName());
		authJsonObject.put("userpassword", authProperties.getSchedulerPassword());
		HttpEntity<String> request = new HttpEntity<String>(authJsonObject.toString(), headers);
		ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);
		
		
		return response.getBody();
	}
	
	public String login() {
		return getAuthToken();
	}

}
