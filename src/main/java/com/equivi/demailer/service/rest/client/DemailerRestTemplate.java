package com.equivi.demailer.service.rest.client;

import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Scope(value = "prototype")
public class DemailerRestTemplate extends RestTemplate {

    private RestTemplate restTemplate;

    public DemailerRestTemplate(){
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }
}
