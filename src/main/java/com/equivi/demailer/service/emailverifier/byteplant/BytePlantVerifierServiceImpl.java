package com.equivi.demailer.service.emailverifier.byteplant;


import com.equivi.demailer.service.constant.dEmailerWebPropertyKey;
import com.equivi.demailer.service.emailverifier.VerifierService;
import com.equivi.demailer.service.rest.client.DemailerRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service(value = "bytePlantVerifierServiceImpl")
public class BytePlantVerifierServiceImpl implements VerifierService {

    private static final Logger LOG = LoggerFactory.getLogger(BytePlantVerifierServiceImpl.class);

    @Resource
    private DemailerRestTemplate restTemplate;

    @Resource(name = "dEmailerWebProperties")
    private Properties webProperties;

    @Override
    public List<String> filterValidEmail(List<String> emailList) {
        return null;
    }

    @Override
    public String getEmailAddressStatus(String emailAddress) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("APIKey", getApiKey());
        parameters.put("EmailAddress", emailAddress);
        parameters.put("Timeout", getApiTimeout());

        String result = restTemplate.getForObject(buildVerifyEmailAddressQueryString(emailAddress), String.class);
        LOG.info("Result:" + result);
        return result;
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
