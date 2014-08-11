package com.equivi.mailsy.service.emailverifier.byteplant;


import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.emailverifier.EmailVerifierResponse;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.equivi.mailsy.service.rest.client.DemailerRestTemplate;
import com.equivi.mailsy.util.WebConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service(value = "bytePlantVerifierServiceImpl")
public class BytePlantVerifierServiceImpl implements VerifierService {

    private static final Logger LOG = LoggerFactory.getLogger(BytePlantVerifierServiceImpl.class);

    private static final String API_KEY = "APIKey";
    private static final String EMAIL_ADDRESS = "EmailAddress";
    private static final String TIMEOUT = "Timeout";

    @Resource
    private DemailerRestTemplate restTemplate;

    @Override
    public List<EmailVerifierResponse> filterValidEmail(List<String> emailList) {
        List<EmailVerifierResponse> emailVerifierResponseList = new ArrayList<>();
        if (!emailList.isEmpty()) {
            for (String emailAddress : emailList) {
                emailVerifierResponseList.add(getEmailAddressStatus(emailAddress));
            }
            return emailVerifierResponseList;
        }
        return new ArrayList<>();
    }

    @Override
    public EmailVerifierResponse getEmailAddressStatus(String emailAddress) {
        EmailVerifierResponse emailVerifierResponse = new EmailVerifierResponse();
        emailVerifierResponse.setAddress(emailAddress);
        emailVerifierResponse.setStatus("Valid Email");
        emailVerifierResponse.setStatusCode("VALID");

        return emailVerifierResponse;
    }

//    @Override
//    public EmailVerifierResponse getEmailAddressStatus(String emailAddress) {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put(API_KEY, getApiKey());
//        parameters.put(EMAIL_ADDRESS, emailAddress);
//        parameters.put(TIMEOUT, getApiTimeout());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//
//        ResponseEntity<String> emailVerifierResponse = restTemplate.getForEntity(buildVerifyEmailAddressQueryString(emailAddress), String.class);
//        LOG.info("Result:" + emailVerifierResponse);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            EmailVerifierResponse emailVerifierResponseEntity = objectMapper.readValue(emailVerifierResponse.getBody(), EmailVerifierResponse.class);
//            emailVerifierResponseEntity.setEmailAddress(emailAddress);
//            return emailVerifierResponseEntity;
//        } catch (IOException e) {
//            LOG.error(e.getMessage(), e);
//        }
//        return null;
//    }

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
        return WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_API_KEY);
    }

    private String getApiTimeout() {
        return WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_API_TIMEOUT);
    }

    private String getVerifierApiUrl() {
        return WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_URL);
    }
}
