package com.equivi.mailsy.service.mailgun;

import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseEventMessage;
import com.equivi.mailsy.util.MailsyStringUtil;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

@Service("mailgunJerseyService")
public class MailgunJerseyEmailServiceImpl implements MailgunService {

    @Resource
    private WebConfiguration webConfiguration;

    @Override
    public String sendMessage(String campaignId, String domain, String from, List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message) {

        Client client = prepareClient();
        WebResource webResource = client.resource(getWebConfigAPIUrlMessage(domain));

        MultivaluedMapImpl form = new MultivaluedMapImpl();
        form.add(MailgunParameters.FROM.getValue(), from);
        form.add(MailgunParameters.TO.getValue(), MailsyStringUtil.buildStringWithSeparator(recipientList, ','));
        form.add(MailgunParameters.CC.getValue(), MailsyStringUtil.buildStringWithSeparator(ccList, ','));
        form.add(MailgunParameters.BCC.getValue(), MailsyStringUtil.buildStringWithSeparator(bccList, ','));
        form.add(MailgunParameters.SUBJECT.getValue(), subject);
        form.add(MailgunParameters.TRACKING.getValue(), "yes");
        form.add(MailgunParameters.TRACKING_CLICKS.getValue(), "yes");
        form.add(MailgunParameters.TRACKING_OPEN.getValue(), "yes");
        form.add(MailgunParameters.HTML.getValue(), message);

        ClientResponse clientResponse = webResource.post(ClientResponse.class, form);

        String output = clientResponse.getEntity(String.class);
        return output;
    }

    private Client prepareClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_API_KEY)));
        return client;
    }

    private String getWebConfigAPIUrlMessage(String domain) {
        StringBuilder sbEndPointURL = new StringBuilder();

        sbEndPointURL.append(webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_WEB_URL));
        sbEndPointURL.append("/");
        sbEndPointURL.append(domain);
        sbEndPointURL.append("/messages");

        return sbEndPointURL.toString();
    }


    @Override
    public MailgunResponseEventMessage getEventForMessageId(String messageId) {
        return null;
    }

    @Override
    public String sendMessageWithAttachment(String campaignId, String domain, String from, List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message, File attachmentFile) {

        if (attachmentFile == null || attachmentFile.length() == 0) {
            throw new MailgunServiceException(MailgunServiceException.ATTACHMENT_EMPTY_EXCEPTION_MESSAGE);
        }

        Client client = prepareClient();
        WebResource webResource = client.resource(getWebConfigAPIUrlMessage(domain));
        FormDataMultiPart form = new FormDataMultiPart();
        form.field(MailgunParameters.FROM.getValue(), from);
        form.field(MailgunParameters.TO.getValue(), MailsyStringUtil.buildStringWithSeparator(recipientList, ','));
        form.field(MailgunParameters.CC.getValue(), MailsyStringUtil.buildStringWithSeparator(ccList, ','));
        form.field(MailgunParameters.BCC.getValue(), MailsyStringUtil.buildStringWithSeparator(bccList, ','));
        form.field(MailgunParameters.SUBJECT.getValue(), subject);
        form.field(MailgunParameters.TRACKING.getValue(), "yes");
        form.field(MailgunParameters.TRACKING_CLICKS.getValue(), "yes");
        form.field(MailgunParameters.TRACKING_OPEN.getValue(), "yes");

        //"<html>Inline image here: <img src=\"cid:test.jpg\"></html>");
        form.field(MailgunParameters.HTML.getValue(), message);

        form.bodyPart(new FileDataBodyPart("inline", attachmentFile,
                MediaType.APPLICATION_OCTET_STREAM_TYPE));
        ClientResponse clientResponse = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).
                post(ClientResponse.class, form);

        String output = clientResponse.getEntity(String.class);
        return output;
    }


    @Override
    public void deleteUnsubscribe(String domain, String emailAddress) {

    }

    @Override
    public void registerUnsubscribe(String domain, String emailAddress) {

    }

    @Override
    public List<String> getUnsubscribeList(String domain) {
        return null;
    }

    @Override
    public boolean isUnsubscribe(String domain, String emailAddress) {
        return false;
    }
}
