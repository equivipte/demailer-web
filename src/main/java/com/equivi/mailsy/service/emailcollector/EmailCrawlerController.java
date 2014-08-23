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

import java.util.Random;

public class EmailCrawlerController implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(EmailCrawlerController.class);

	private String site;
    private CrawlController controller;

    @Override
	public void run() {
        try {
            String[] crawlerDomains = new String[] { site };
            controller.setCustomData(crawlerDomains);
            controller.addSeed(site);
            controller.startNonBlocking(EmailCrawler.class, 5);
            controller.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Finished crawling");
	}

	public void setSite(String site) {
		this.site = site;
	}

    public void setController(CrawlController controller) {
        this.controller = controller;
    }
}
