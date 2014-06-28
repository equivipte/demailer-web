package com.equivi.mailsy.service.emailcollector;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.shutdown.Hook;
import com.equivi.mailsy.shutdown.ShutdownService;
import com.equivi.mailsy.util.EmailCrawler;

@Service
public class EmailCollectorServiceImpl implements EmailCollectorService, Runnable {
	private static final Logger logger = LoggerFactory.getLogger(EmailCollectorServiceImpl.class);

	private final BlockingQueue<DeferredResult<EmailCollectorMessage>> resultQueue = new LinkedBlockingQueue<>();
	
	private Thread thread;
	
	private volatile boolean start = true;
	
	@Autowired
	private ShutdownService shutdownService;
	
	@Autowired
	@Qualifier("emailCrawlerController")
	private EmailCrawlerController emailCrawlerController;
	
	private Hook hook;
	
	@Override
	public void run() {
		logger.info("EmailCollectorServiceImpl - Thread running");
		System.out.println("EmailCollectorServiceImpl - Thread running");
		while(hook.keepRunning()) {
			try {
				DeferredResult<EmailCollectorMessage> result = resultQueue.take();
				
				EmailCollectorMessage message = EmailCrawler.queue.take();
				
				result.setResult(message);
				
			} catch (InterruptedException e) {
				System.out.println("Interrupted when waiting for latest update. "
						+ e.getMessage());
			}
		}
	}
	
	@Override
	public void subscribe(String site) throws Exception { 
		logger.info("Starting email crawler...");
		System.out.println("Starting email crawler...");
		
		emailCrawlerController.setSite(site); 
		emailCrawlerController.start();
        
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
}
