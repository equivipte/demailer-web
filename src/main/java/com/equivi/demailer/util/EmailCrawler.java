package com.equivi.demailer.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.equivi.demailer.dto.emailer.EmailCollectorMessage;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class EmailCrawler extends WebCrawler {
	
	public static final BlockingQueue<EmailCollectorMessage> queue = new LinkedBlockingQueue<>(); 

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
												+ "|png|tiff?|mid|mp2|mp3|mp4"
												+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
												+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private String[] crawlDomains;

	@Override
	public void onStart() {
		crawlDomains = (String[]) myController.getCustomData();
	}

	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) {
			return false;
		}
		for (String crawlDomain : crawlDomains) {
			if (href.startsWith(crawlDomain)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();

		System.out.println("URL: " + url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			
			try {
				addEmail(html);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("=============");
	}
	
	public void addEmail(String html) throws InterruptedException{ 
		final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
	    Pattern p = Pattern.compile(RE_MAIL);
	    Matcher m = p.matcher(html);

	    while(m.find()) {
	    	queue.add(new EmailCollectorMessage(m.group(1)));
	    	
	    	System.out.println("Email found ---> " + m.group(1)); 
	    }
	}
}