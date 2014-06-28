package com.equivi.demailer.service.emailcollector;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.equivi.demailer.util.EmailCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class EmailCrawlerController implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(EmailCrawlerController.class);
	
	private volatile boolean start = true;
	
	private String site;
	
	public void start() throws Exception {
		if (start) {
			synchronized (this) {
				if (start) {
					start = false;
					System.out.println("Starting EmailCrawlerController ...");
					
					Thread thread = new Thread(this, "EmailCrawling");
					thread.start();
				}
			}
		} else {
			System.out.println("Crawling in progress");
		}
	}
	
	@Override
	public void run() {
		String crawlStorageFolder = "/opt/demailer/data";
		
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
			CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
			
			String[] crawlerDomains = new String[] { site };

	        controller.setCustomData(crawlerDomains);
	        controller.addSeed(site);
	        controller.startNonBlocking(EmailCrawler.class, 5);
	        controller.waitUntilFinish();

		} catch (Exception e) {
			e.printStackTrace();
		}

		start = true;
                
        System.out.println("Finished crawling");
	}

	public void setSite(String site) {
		this.site = site;
	}
	
}
