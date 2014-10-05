package com.equivi.mailsy.service.campaign.queue;

import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.MailDeliveryStatus;
import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.contact.ContactManagementService;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.subsriber.SubscriberGroupService;
import com.equivi.mailsy.util.WebConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueueCampaignMailerConverter {

    @Resource
    private SubscriberGroupService subscriberGroupService;

    @Resource
    private ContactManagementService contactManagementService;

    @Resource
    private MailgunService mailgunService;

    public List<QueueCampaignMailerEntity> convertToQueueCampaignMailerList(List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList) {
        List<QueueCampaignMailerEntity> queueCampaignMailerEntityList = new ArrayList<>();
        for (CampaignSubscriberGroupEntity campaignSubscriberGroupEntity : campaignSubscriberGroupEntityList) {

            SubscriberGroupEntity subscriberGroupEntity = campaignSubscriberGroupEntity.getSubscriberGroupEntity();

            List<SubscriberContactEntity> subscriberContactEntityList = subscriberGroupService.getSubscriberContactList(subscriberGroupEntity);

            if (subscriberContactEntityList != null && !subscriberContactEntityList.isEmpty()) {
                for (SubscriberContactEntity subscriberContactEntity : subscriberContactEntityList) {
                    if (subscriberContactEntity.getContactEntity().getSubscribeStatus().equals(SubscribeStatus.SUBSCRIBED)) {

                        //Check latest unsubscribe status at Mailgun
                        if (mailgunService.isUnsubscribe(null, subscriberContactEntity.getContactEntity().getEmailAddress())) {
                            updateContactEntityToUnsubscribed(subscriberContactEntity);
                        } else {
                            QueueCampaignMailerEntity queueCampaignMailerEntity = new QueueCampaignMailerEntity();
                            queueCampaignMailerEntity.setCampaignId(campaignSubscriberGroupEntity.getCampaignEntity().getId());

                            queueCampaignMailerEntity.setContent(replaceEmailContentWithParams(subscriberContactEntity,
                                    campaignSubscriberGroupEntity.getCampaignEntity().getEmailContent()));
                            queueCampaignMailerEntity.setSubject(campaignSubscriberGroupEntity.getCampaignEntity().getEmailSubject());
                            queueCampaignMailerEntity.setEmailFrom(campaignSubscriberGroupEntity.getCampaignEntity().getEmailFrom());
                            queueCampaignMailerEntity.setRecipient(subscriberContactEntity.getContactEntity().getEmailAddress());
                            queueCampaignMailerEntity.setScheduledSendDate(campaignSubscriberGroupEntity.getCampaignEntity().getScheduledSendDate());

                            //Set Mail Delivery status to PENDING send
                            queueCampaignMailerEntity.setMailDeliveryStatus(MailDeliveryStatus.PENDING);


                            if (!queueCampaignMailerEntityList.contains(queueCampaignMailerEntity)) {
                                queueCampaignMailerEntityList.add(queueCampaignMailerEntity);
                            }
                        }
                    }
                }
            }
        }
        return queueCampaignMailerEntityList;
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

}
