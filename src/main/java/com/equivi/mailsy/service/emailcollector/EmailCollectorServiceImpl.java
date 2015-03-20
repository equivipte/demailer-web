package com.equivi.mailsy.service.emailcollector;

import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.util.EmailCrawler;
import com.equivi.mailsy.util.WebConfigUtil;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailCollectorServiceImpl implements EmailCollectorService {
	private static final Logger logger = LoggerFactory.getLogger(EmailCollectorServiceImpl.class);

	private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static CrawlController controller;

	@Override
	public void subscribe(String site) throws Exception { 
		logger.info("Starting email crawler...");

        clearQueue();

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

        try {
            controller= new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

		EmailCrawlerController emailCrawlerController = new EmailCrawlerController();
		emailCrawlerController.setSite(site);
        emailCrawlerController.setController(controller);
		
		executor.execute(emailCrawlerController);
	}

    @Override
	public boolean getUpdateCrawlingStatus() {
		boolean terminated = controller.isFinished();

		if(terminated) {
            clearQueue();
		}

		return terminated;
	}

    @Override
    public void cancel() {
        if(controller != null && !controller.isFinished()) {
            controller.shutdown();
            controller.waitUntilFinish();

            clearQueue();
        }
    }

    private void clearQueue() {
        EmailCrawler.queue.clear();
        EmailCrawler.urlQueue.clear();
    }

}
