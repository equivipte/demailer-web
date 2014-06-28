package com.equivi.mailsy.service.emailverifier.byteplant;


import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.equivi.mailsy.service.rest.client.DemailerRestTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service(value = "bytePlantVerifierServiceImpl")
public class BytePlantVerifierServiceImpl implements VerifierService {

    private static final Logger LOG = LoggerFactory.getLogger(BytePlantVerifierServiceImpl.class);

    private static final String API_KEY = "APIKey";
    private static final String EMAIL_ADDRESS = "EmailAddress";
    private static final String TIMEOUT = "Timeout";

    @Resource
    private DemailerRestTemplate restTemplate;

    @Resource(name = "dEmailerWebProperties")
    private Properties webProperties;

    @Override
    public List<EmailVerifierResponse> filterValidEmail(List<String> emailList) {
        List<EmailVerifierResponse> emailVerifierResponseList = new ArrayList<>();
        if (!emailList.isEmpty()) {
            for (String emailAddress : emailList) {
                emailVerifierResponseList.add(getEmailAddressStatus(emailAddress));
            }
        }
        return new ArrayList<>();
    }

    @Override
    public EmailVerifierResponse getEmailAddressStatus(String emailAddress) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(API_KEY, getApiKey());
        parameters.put(EMAIL_ADDRESS, emailAddress);
        parameters.put(TIMEOUT, getApiTimeout());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> emailVerifierResponse = restTemplate.getForEntity(buildVerifyEmailAddressQueryString(emailAddress), String.class);
        LOG.info("Result:" + emailVerifierResponse);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmailVerifierResponse emailVerifierResponseEntity = objectMapper.readValue(emailVerifierResponse.getBody(), EmailVerifierResponse.class);
            emailVerifierResponseEntity.setEmailAddress(emailAddress);
            return emailVerifierResponseEntity;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    private String buildVerifyEmailAddressQueryString(String emailAddress) {
        StringBuilder sbQueryEmailAddress = new StringBuilder();
        sbQueryEmailAddress.append(getVerifierApiUrl());
        sbQueryEmailAddress.append("?EmailAddress=");
        sbQueryEmailAddress.append(emailAddress);
        sbQueryEmailAddress.append("&APIKey=");
        sbQueryEmailAddress.append(getApiKey());
        sbQueryEmailAddress.append("&Timeout=");
        sbQueryEmailAddress.append(getApiTimeout());
        return sbQueryEmailAddress.toString();
    }

    private String getApiKey() {
        return webProperties.getProperty(dEmailerWebPropertyKey.EMAIL_VERIFIER_API_KEY.getKeyName());
    }

    private String getApiTimeout() {
        return webProperties.getProperty(dEmailerWebPropertyKey.EMAIL_VERIFIER_API_TIMEOUT.getKeyName());
    }

    private String getVerifierApiUrl() {
        return webProperties.getProperty(dEmailerWebPropertyKey.EMAIL_VERIFIER_URL.getKeyName());
    }
}
