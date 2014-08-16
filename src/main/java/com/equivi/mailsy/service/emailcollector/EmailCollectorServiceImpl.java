package com.equivi.mailsy.service.emailcollector;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
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

@Service
public class EmailCollectorServiceImpl implements EmailCollectorService, Runnable {
	private static final Logger logger = LoggerFactory.getLogger(EmailCollectorServiceImpl.class);

	private final BlockingQueue<DeferredResult<EmailCollectorMessage>> resultQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<EmailCollectorMessage> duplicateQueue = new LinkedBlockingQueue<>();

	private Thread thread;
	
	private volatile boolean start = true;
	
	@Autowired
	private ShutdownService shutdownService;
	
	private Hook hook;
	
	private ExecutorService executor;
	
	@Override
	public void run() {
		logger.info("EmailCollectorServiceImpl - Thread running");
		while(hook.keepRunning()) {
			try {
				DeferredResult<EmailCollectorMessage> result = resultQueue.take();
				EmailCollectorMessage message = EmailCrawler.queue.take();

                if(duplicateQueue.isEmpty() || !duplicateQueue.contains(message)) {
                    result.setResult(message);
                }

                duplicateQueue.add(message);

			} catch (InterruptedException e) {
				logger.warn("Interrupted when waiting for latest update. "
                        + e.getMessage());
			}
		}
	}
	
	@Override
	public void subscribe(String site) throws Exception { 
		logger.info("Starting email crawler...");

		EmailCrawlerController emailCrawlerController = new EmailCrawlerController();
		emailCrawlerController.setSite(site); 
		
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		executor.execute(emailCrawlerController); 
		executor.shutdown();

        startThread();
	}
	
	private void startThread() {
		if(start) {
			synchronized (this) {
				start = false;
				
				thread = new Thread(this, "CollectorService");
				hook = shutdownService.createHook(thread);
				
				thread.start();
			}
		}
	}

	@Override
	public void getUpdate(DeferredResult<EmailCollectorMessage> result) {
		resultQueue.add(result);
	}

    @Override
	public boolean getUpdateCrawlingStatus() {
		boolean terminated = executor.isTerminated();
		
		if(terminated) {
			resultQueue.clear();
            EmailCrawler.queue.clear();
            duplicateQueue.clear();
		}
		
		return terminated;
	}
}
