package com.equivi.mailsy.service.emailverifier.webemailverifier;

import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.emailverifier.EmailVerifierResponse;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.equivi.mailsy.service.rest.client.DemailerRestTemplate;
import com.equivi.mailsy.util.WebConfigUtil;
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
import java.util.List;

@Service("webEmailVerifierImpl")
public class WebEmailVerifierImpl implements VerifierService {

    private static final Logger LOG = LoggerFactory.getLogger(WebEmailVerifierImpl.class);

    private static final String OPERATION = "CheckEmail";

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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        LOG.info("Send to Email Verifier API");
        restTemplate.setHostName(getVerifierApiUrl()).setUserName("").setPassword("");
        ResponseEntity<String> emailVerifierResponse = restTemplate.getForEntity(buildVerifyEmailAddressQueryString(emailAddress), String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LOG.info("Result:" + emailVerifierResponse.getBody());

            EmailVerifierResponse emailVerifierResponseEntity = objectMapper.readValue(emailVerifierResponse.getBody(), EmailVerifierResponse.class);
            return emailVerifierResponseEntity;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    private String buildVerifyEmailAddressQueryString(String emailAddress) {
        StringBuilder sbQueryEmailAddress = new StringBuilder();
        sbQueryEmailAddress.append(getVerifierApiUrl());
        sbQueryEmailAddress.append("/");
        sbQueryEmailAddress.append(OPERATION);
        sbQueryEmailAddress.append("/");
        sbQueryEmailAddress.append(getApiKey());
        sbQueryEmailAddress.append("/");
        sbQueryEmailAddress.append(emailAddress);
        return sbQueryEmailAddress.toString();
    }

    private String getApiKey() {
        return WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_API_KEY);
    }

    private String getVerifierApiUrl() {
        return WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_URL);
    }
}
