package com.equivi.mailsy.service.emailcollector;

import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.BlockingQueue;

/**
 * Created by tsagita on 2/8/14.
 */
public interface EmailScanningService {
    void getUrlScanningUpdate(DeferredResult<EmailCollectorUrlMessage> result);

    void subscribe() throws Exception;

    BlockingQueue<DeferredResult<EmailCollectorUrlMessage>> getQueue();
}
