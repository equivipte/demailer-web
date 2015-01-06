package com.equivi.mailsy.service.campaign.queue;

import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.data.entity.QueueProcessed;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.contact.ContactManagementService;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.subsriber.SubscriberGroupService;
import com.equivi.mailsy.util.WebConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueueCampaignMailerConverter {

    private static final Logger LOG = LoggerFactory.getLogger(QueueCampaignMailerConverter.class);
    @Resource
    private SubscriberGroupService subscriberGroupService;
    @Resource
    private ContactManagementService contactManagementService;
    @Resource(name = "mailgunRestTemplateEmailService")
    private MailgunService mailgunService;

    public List<QueueCampaignMailerEntity> convertToQueueCampaignMailerList(List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList) {
        List<QueueCampaignMailerEntity> queueCampaignMailerEntityList = new ArrayList<>();
        for (CampaignSubscriberGroupEntity campaignSubscriberGroupEntity : campaignSubscriberGroupEntityList) {

            SubscriberGroupEntity subscriberGroupEntity = campaignSubscriberGroupEntity.getSubscriberGroupEntity();

            List<SubscriberContactEntity> subscriberContactEntityList = subscriberGroupService.getSubscriberContactList(subscriberGroupEntity);

            if (subscriberContactEntityList != null && !subscriberContactEntityList.isEmpty()) {
                for (SubscriberContactEntity subscriberContactEntity : subscriberContactEntityList) {
                    if (isSubscribed(subscriberContactEntity)) {
                        //Check latest unsubscribe status at Mailgun
                        if (mailgunService.checkIfEmailAddressHasBeenUnsubscribed(null, subscriberContactEntity.getContactEntity().getEmailAddress())) {
                            updateContactEntityToUnsubscribed(subscriberContactEntity);
                        } else {
                            QueueCampaignMailerEntity queueCampaignMailerEntity = createQueueCampaignMailerEntity(campaignSubscriberGroupEntity, subscriberContactEntity);

                            if (isUniqueCampaign(queueCampaignMailerEntityList, queueCampaignMailerEntity)) {
                                LOG.info("Add email to queue:" + queueCampaignMailerEntity.getRecipient());
                                queueCampaignMailerEntityList.add(queueCampaignMailerEntity);
                            }
                        }
                    }
                }
            }
        }
        return queueCampaignMailerEntityList;
    }

    private boolean isSubscribed(SubscriberContactEntity subscriberContactEntity) {
        return subscriberContactEntity.getContactEntity().getSubscribeStatus().equals(SubscribeStatus.SUBSCRIBED);
    }

    private QueueCampaignMailerEntity createQueueCampaignMailerEntity(CampaignSubscriberGroupEntity campaignSubscriberGroupEntity, SubscriberContactEntity subscriberContactEntity) {
        QueueCampaignMailerEntity queueCampaignMailerEntity = new QueueCampaignMailerEntity();
        queueCampaignMailerEntity.setCampaignId(campaignSubscriberGroupEntity.getCampaignEntity().getId());

        String replaceContent = replaceEmailContentWithParams(subscriberContactEntity, campaignSubscriberGroupEntity.getCampaignEntity().getEmailContent());
        replaceContent = replaceUnsubscribeURL(subscriberContactEntity, replaceContent);

        queueCampaignMailerEntity.setContent(replaceContent);
        queueCampaignMailerEntity.setSubject(campaignSubscriberGroupEntity.getCampaignEntity().getEmailSubject());
        queueCampaignMailerEntity.setEmailFrom(campaignSubscriberGroupEntity.getCampaignEntity().getEmailFrom());
        queueCampaignMailerEntity.setRecipient(subscriberContactEntity.getContactEntity().getEmailAddress());
        queueCampaignMailerEntity.setScheduledSendDate(campaignSubscriberGroupEntity.getCampaignEntity().getScheduledSendDate());

        //Set Mail Delivery status to PENDING send
        queueCampaignMailerEntity.setQueueProcessed(QueueProcessed.PENDING.getStatus());
        return queueCampaignMailerEntity;
    }

    private boolean isUniqueCampaign(List<QueueCampaignMailerEntity> queueCampaignMailerEntityList, QueueCampaignMailerEntity queueCampaignMailerEntity) {
        return !queueCampaignMailerEntityList.contains(queueCampaignMailerEntity);
    }

    private void updateContactEntityToUnsubscribed(SubscriberContactEntity subscriberContactEntity) {
        ContactEntity contactEntity = contactManagementService.getContactEntityByEmailAddress(subscriberContactEntity.getContactEntity().getEmailAddress());
        contactEntity.setSubscribeStatus(SubscribeStatus.UNSUBSCRIBED);

        contactManagementService.saveContactEntity(contactEntity);
    }

    private String replaceEmailContentWithParams(SubscriberContactEntity contactEntity, String emailContent) {
        String emailContentWithParams = WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_CONTENT_PARAMS);
        String[] arrParams = StringUtils.split(emailContentWithParams, ',');
        String[] arrValues = new String[]{contactEntity.getContactEntity().getFirstName(),
                contactEntity.getContactEntity().getLastName(), contactEntity.getContactEntity().getCompanyName()};

        return StringUtils.replaceEachRepeatedly(emailContent, arrParams, arrValues);
    }

    private String replaceUnsubscribeURL(SubscriberContactEntity contactEntity, String emailContent) {
        String unsubscribeURL = buildUnsubscribeURL(contactEntity.getContactEntity().getEmailAddress());

        return StringUtils.replace(emailContent, "%unsubscribe_url%", unsubscribeURL);
    }

    String buildUnsubscribeURL(String emailAddress) {

        StringBuilder sbUnsubscribeURL = new StringBuilder();
        sbUnsubscribeURL.append(WebConfigUtil.getValue(dEmailerWebPropertyKey.MAILSY_SERVER_URL));
        sbUnsubscribeURL.append("/unsubscribe");
        sbUnsubscribeURL.append("?emailToUnsubscribe=");
        sbUnsubscribeURL.append(emailAddress);
        sbUnsubscribeURL.append("&domainName=");
        sbUnsubscribeURL.append(WebConfigUtil.getValue(dEmailerWebPropertyKey.DOMAIN_NAME));

        return sbUnsubscribeURL.toString();
    }

}
