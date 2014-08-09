package com.equivi.mailsy.service.emailcollector;

import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import com.equivi.mailsy.shutdown.Hook;
import com.equivi.mailsy.shutdown.ShutdownService;
import com.equivi.mailsy.util.EmailCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tsagita on 2/8/14.
 */

@Service
public class EmailScanningServiceImpl implements EmailScanningService, Runnable {
    private static final Logger logger = LoggerFactory.getLogger(EmailScanningServiceImpl.class);

    private final BlockingQueue<DeferredResult<EmailCollectorUrlMessage>> resultUrlQueue = new LinkedBlockingQueue<>();

    private Thread thread;

    private volatile boolean start = true;

    @Autowired
    private ShutdownService shutdownService;

    private Hook hook;


    @Override
    public void run() {
        logger.info("EmailCollectorServiceImpl - Thread running");

        while (hook.keepRunning()) {
            try {
                DeferredResult<EmailCollectorUrlMessage> resultUrl = resultUrlQueue.take();
                EmailCollectorUrlMessage urlMessage = EmailCrawler.urlQueue.take();
                resultUrl.setResult(urlMessage);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when waiting for latest update. "
                        + e.getMessage());
            }
        }
    }

    @Override
    public void subscribe() throws Exception {
        startThread();
    }

    @Override
    public void getUrlScanningUpdate(DeferredResult<EmailCollectorUrlMessage> result) {
        resultUrlQueue.add(result);
    }

    private void startThread() {
        if (start) {
            synchronized (this) {
                start = false;

                thread = new Thread(this, "EmailScanning");
                hook = shutdownService.createHook(thread);

                thread.start();
            }
        }
    }

    @Override
    public BlockingQueue<DeferredResult<EmailCollectorUrlMessage>> getQueue() {
        return resultUrlQueue;
    }
}
