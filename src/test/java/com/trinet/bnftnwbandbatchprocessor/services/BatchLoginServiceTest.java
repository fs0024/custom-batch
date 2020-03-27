package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.conf.properties.AuthProperties;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchLoginServiceTest {



    private static String authUrl = "http://dummyUrl";
    private static String schedulerUserName = "testuser";
    private static String schedulerPassword = "pwd";
    private static String responseString = "token";


    @TestConfiguration
    static class TestConfig {
        @Bean
        public BatchLoginService batchLoginServiceBuilder() {
            return new BatchLoginService();
        }





    }
    @Mock
    private static AuthProperties authProperties;
    @Mock
    private static RestTemplate restTemplate;

    @InjectMocks
    private static BatchLoginService batchLoginService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void  testBatchLoginService() {




        HttpHeaders headers = new HttpHeaders();
        JSONObject authJsonObject = new JSONObject();
        headers.setContentType(MediaType.APPLICATION_JSON);
        authJsonObject.put("guid", schedulerUserName);
        authJsonObject.put("userpassword", schedulerPassword);
        HttpEntity<String> request = new HttpEntity<>(authJsonObject.toString(), headers);
        HttpEntity<String> requestSpy = Mockito.spy(request);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseString,HttpStatus.OK);


        //Mockito.when(authProperties.getAuthUrl()).thenReturn(authUrl);
        Mockito.when(authProperties.getSchedulerUserName()).thenReturn(schedulerUserName);
        Mockito.when(authProperties.getSchedulerPassword()).thenReturn(schedulerPassword);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));


        assertEquals(expectedResponse.getBody(),batchLoginService.login());


    }

}