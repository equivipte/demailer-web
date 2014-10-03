package com.equivi.mailsy.service.mailgun;

import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.mail.Attachment;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseEventMessage;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseMessage;
import com.equivi.mailsy.service.rest.client.DemailerRestTemplate;
import com.equivi.mailsy.util.StringUtil;
import com.equivi.mailsy.web.constant.WebConfiguration;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MailgunEmailServiceImpl implements MailgunService {

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private DemailerRestTemplate demailerRestTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(MailgunEmailServiceImpl.class);

    private static final String API_USERNAME = "api";

    private static final String DOMAIN_SANDBOX = "sandbox80dd6c12cf4c4f99bdfa256bfea7cfeb.mailgun.org";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String sendMessage(String campaignId, String domain, String from, List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message) {

        if (domain == null) {
            domain = DOMAIN_SANDBOX;
        }
        String mailgunWebURLApi = buildSendMessageURL(campaignId, domain, from, recipientList, ccList, bccList, subject, message);

        setupHttpClientCredentials(mailgunWebURLApi);

        ResponseEntity<String> response = demailerRestTemplate.postForEntity(mailgunWebURLApi, buildHttpEntity(), String.class);

        if (response != null) {
            try {
                LOG.info("HTTP Status code :" + response.getStatusCode());

                String responseBody = response.getBody();

                LOG.debug("Response From mailgun:" + responseBody);

                MailgunResponseMessage mailgunResponseMessage = objectMapper.readValue(responseBody, MailgunResponseMessage.class);

                return mailgunResponseMessage.getId();
            } catch (JsonMappingException e) {
                LOG.error(e.getMessage(), e);
            } catch (JsonParseException e) {
                LOG.error(e.getMessage(), e);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            LOG.error("Unable to get Response during send message");
        }

        return null;
    }

    private void setupHttpClientCredentials(String mailgunWebURLApi) {
        String mailgunAPIKey = webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_API_KEY);

        demailerRestTemplate.setCredentials(API_USERNAME, mailgunAPIKey)
                .setHostName(mailgunWebURLApi);
        demailerRestTemplate.setHttpAsyncClientFactory();
    }

    @Override
    public MailgunResponseEventMessage getEventForMessageId(String messageId) {
        String mailgunEventAPIURL = formatMailgunHostUrl(getMailgunBaseAPIURL(), DOMAIN_SANDBOX, StringUtil.buildQueryParameters(buildEventParameter(messageId)), MailgunAPIType.EVENTS.getValue());

        setupHttpClientCredentials(mailgunEventAPIURL);

        ResponseEntity<String> response = demailerRestTemplate.getForEntity(mailgunEventAPIURL, String.class);

        if (response != null) {
            try {
                LOG.info("HTTP Status code :" + response.getStatusCode());

                String responseBody = response.getBody();

                LOG.debug("Response From mailgun:" + responseBody);

                MailgunResponseEventMessage mailgunResponseEventMessage = objectMapper.readValue(responseBody, MailgunResponseEventMessage.class);

                return mailgunResponseEventMessage;
            } catch (JsonMappingException e) {
                LOG.error(e.getMessage(), e);
            } catch (JsonParseException e) {
                LOG.error(e.getMessage(), e);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            LOG.error("Unable to get Response during send message");
        }

        return null;
    }

    private String buildSendMessageURL(String campaignId, String domain, String from, List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message) {
        Map<String, String> mailParameter = buildSendParameters(campaignId, from, recipientList, ccList, bccList, subject, message);

        return formatMailgunHostUrl(getMailgunBaseAPIURL(), domain, StringUtil.buildQueryParameters(mailParameter), MailgunAPIType.MESSAGES.getValue());
    }

    String formatMailgunHostUrl(String mailgunBaseURL, String domain, String queryParameters, String resource) {

        StringBuilder builderMailgunURL = new StringBuilder();
        builderMailgunURL.append(mailgunBaseURL);
        builderMailgunURL.append("/");
        builderMailgunURL.append(domain);
        builderMailgunURL.append("/");
        builderMailgunURL.append(resource);

        if (!StringUtils.isEmpty(queryParameters)) {
            builderMailgunURL.append("?");
            builderMailgunURL.append(queryParameters);
        }

        return builderMailgunURL.toString();
    }

    HttpEntity buildHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(httpHeaders);
    }


    Map<String, String> buildSendParameters(String campaignId, String from, List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message) {
        Map<String, String> mailParameter = new HashMap<>();
        //mailParameter.put(MailgunParameters.CAMPAIGN_ID.getValue(), campaignId);
        mailParameter.put(MailgunParameters.FROM.getValue(), from);
        mailParameter.put(MailgunParameters.TO.getValue(), StringUtil.buildStringWithSeparator(recipientList, ','));
        mailParameter.put(MailgunParameters.CC.getValue(), StringUtil.buildStringWithSeparator(ccList, ','));
        mailParameter.put(MailgunParameters.BCC.getValue(), StringUtil.buildStringWithSeparator(bccList, ','));
        mailParameter.put(MailgunParameters.SUBJECT.getValue(), subject);
        mailParameter.put(MailgunParameters.HTML.getValue(), StringEscapeUtils.unescapeHtml4(message));
        mailParameter.put(MailgunParameters.TRACKING.getValue(), "yes");
        mailParameter.put(MailgunParameters.TRACKING_CLICKS.getValue(), "yes");
        mailParameter.put(MailgunParameters.TRACKING_OPEN.getValue(), "yes");

        return mailParameter;
    }

    Map<String, String> buildEventParameter(String messageId) {
        Map<String, String> mailParameter = new HashMap<>();
        mailParameter.put(MailgunParameters.MESSAGE_ID.getValue(), messageId);
        return mailParameter;
    }


    @Override
    public void sendMailWithAttachment(List<String> recipientList, List<Attachment> attachmentList, List<String> ccList, List<String> bccList, String subject, String message) {

    }


    String getMailgunBaseAPIURL() {
        return webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_WEB_URL);
    }
}