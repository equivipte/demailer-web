package com.equivi.demailer.service.rest.client;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = "prototype")
public class DemailerRestTemplate extends RestTemplate {

    private RestTemplate restTemplate;

    public DemailerRestTemplate(){
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        MappingJacksonHttpMessageConverter mappingJacksonHttpConverter = new MappingJacksonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(new MediaType("text","plain"));
        supportedMediaTypes.add(new MediaType("application","json"));

        mappingJacksonHttpConverter.setSupportedMediaTypes(supportedMediaTypes);

        restTemplate.getMessageConverters().add(mappingJacksonHttpConverter);
    }
}
