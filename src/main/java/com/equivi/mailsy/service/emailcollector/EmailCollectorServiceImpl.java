package com.equivi.mailsy.service.emailcollector;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.shutdown.Hook;
import com.equivi.mailsy.shutdown.ShutdownService;
import com.equivi.mailsy.util.EmailCrawler;
import com.equivi.mailsy.util.WebConfigUtil;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Random;
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
    private CrawlController controller;
	
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

                if(EmailCrawler.queue.contains(message)) {
                    for (EmailCollectorMessage messageFromQueue : EmailCrawler.queue) {
                        if(message.equals(messageFromQueue)) {
                            EmailCrawler.queue.remove(messageFromQueue);
                        }
                    }
                }

                if(!resultQueue.isEmpty()) {
                    resultQueue.clear();
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

        String crawlStorageFolder = WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_CRAWLING_STORAGE);

        CrawlConfig config = new CrawlConfig();

        Random rand = new Random();

        config.setCrawlStorageFolder(crawlStorageFolder + "/crawler" + rand.nextInt(Integer.MAX_VALUE));
        config.setPolitenessDelay(1000);
        config.setMaxPagesToFetch(-1);
        config.setMaxDepthOfCrawling(20);
        config.setIncludeHttpsPages(true) ;

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        controller = new CrawlController(config, pageFetcher, robotstxtServer);

		EmailCrawlerController emailCrawlerController = new EmailCrawlerController();
		emailCrawlerController.setSite(site);
        emailCrawlerController.setController(controller);
		
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
            clearQueue();
		}

		return terminated;
	}

    @Override
    public void cancel() {
        if(executor != null && !executor.isTerminated() && controller != null) {
            controller.shutdown();
            controller.waitUntilFinish();

            executor.shutdown();

            clearQueue();
        }
    }

    private void clearQueue() {
        EmailScanningServiceImpl.resultUrlQueue.clear();
        resultQueue.clear();
        duplicateQueue.clear();
        EmailCrawler.queue.clear();
        EmailCrawler.urlQueue.clear();
    }

}
