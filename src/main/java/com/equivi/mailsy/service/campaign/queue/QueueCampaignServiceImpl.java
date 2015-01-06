package com.equivi.mailsy.service.campaign.queue;

import com.equivi.mailsy.data.dao.QueueCampaignMailerDao;
import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.QQueueCampaignMailerEntity;
import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.data.entity.QueueProcessed;
import com.equivi.mailsy.dto.quota.QuotaDTO;
import com.equivi.mailsy.service.quota.QuotaService;
import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class QueueCampaignServiceImpl implements QueueCampaignService {

    private static final Logger LOG = LoggerFactory.getLogger(QueueCampaignServiceImpl.class);
    @Resource
    private QueueCampaignMailerDao queueCampaignMailerDao;
    @Resource
    private QueueCampaignMailerConverter queueCampaignMailerConverter;
    @Autowired
    private QuotaService quotaService;

    @Override
    @Transactional(readOnly = false)
    public void sendCampaignToQueueMailer(List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList) {

        if (campaignSubscriberGroupEntityList != null && !campaignSubscriberGroupEntityList.isEmpty()) {

            List<QueueCampaignMailerEntity> queueCampaignMailerEntityList = queueCampaignMailerConverter.convertToQueueCampaignMailerList(campaignSubscriberGroupEntityList);

            if (queueCampaignMailerEntityList != null && !queueCampaignMailerEntityList.isEmpty()) {
                queueCampaignMailerDao.save(queueCampaignMailerEntityList);
            } else {
                LOG.error("Nothing to put to queue");
            }
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<QueueCampaignMailerEntity> getEmailQueueToSend() {
        QuotaDTO quota = quotaService.getQuota();
        long emailSendingQuota = quota.getEmailSendingQuota();
        long currentEmailsSent = quota.getCurrentEmailsSent();

        if (currentEmailsSent <= emailSendingQuota) {
            Iterable<QueueCampaignMailerEntity> queueCampaignMailerEntityIterable = queueCampaignMailerDao.findAll(getEmailQueueToSendPredicate());

            if (queueCampaignMailerEntityIterable.iterator().hasNext()) {
                int remainingEmailQuota = (int) (emailSendingQuota - currentEmailsSent);
                List<QueueCampaignMailerEntity> queueCampaignMailerEntities = Lists.newArrayList(queueCampaignMailerEntityIterable);

                if (remainingEmailQuota < queueCampaignMailerEntities.size()) {

                    return queueCampaignMailerEntities.subList(0, remainingEmailQuota);
                } else {
                    return queueCampaignMailerEntities;
                }
            }
        }

        return null;
    }

    private Predicate getEmailQueueToSendPredicate() {
        QQueueCampaignMailerEntity qQueueCampaignMailerEntity = QQueueCampaignMailerEntity.queueCampaignMailerEntity;

        BooleanBuilder booleanEmailQueueToSend = new BooleanBuilder();

        Date now = new Date();
        booleanEmailQueueToSend.or(qQueueCampaignMailerEntity.queueProcessed.ne(QueueProcessed.SUCCESS.getStatus()));
        booleanEmailQueueToSend.and(qQueueCampaignMailerEntity.scheduledSendDate.before(now)).or(qQueueCampaignMailerEntity.scheduledSendDate.eq(now));

        return booleanEmailQueueToSend;
    }


}
