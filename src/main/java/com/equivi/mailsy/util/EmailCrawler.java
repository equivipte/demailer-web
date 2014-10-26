package com.equivi.mailsy.util;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCrawler extends WebCrawler {

	private final String RE_MAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final Pattern PATTERN = Pattern.compile(RE_MAIL);

	public static final BlockingQueue<EmailCollectorMessage> queue = new LinkedBlockingQueue<>();
    public static final BlockingQueue<EmailCollectorUrlMessage> urlQueue = new LinkedBlockingQueue<>();

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(ashx|youtube|css|axd|js|bmp|gif|jpe?g|doc|png|xls|xlsx|tiff?|mid|mp2|mp3|htm|csv|pptx|ppt|mp4|wav|avi|mov|mpeg|ram|m4v|rm|smil|wmv|swf|wma|rar|gz))$");

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

        urlQueue.add(new EmailCollectorUrlMessage(url));

		logger.info("Scanning URL: " + url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			
			try {
				addEmail(html);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		logger.info("=============");
	}
	
	public void addEmail(String html) throws InterruptedException{
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");

        // find email from text
	    Matcher m = PATTERN.matcher(doc.text());

	    while(m.find()) {
            addIntoQueue(m);
	    }

        // find email from links
        for (Element link : links) {
            m = PATTERN.matcher(link.attr("href"));
            while(m.find()) {
                addIntoQueue(m);
            }
        }
	}

    private void addIntoQueue(Matcher m) {
        String emailFound = m.group(0);
        EmailCollectorMessage collectorMessage = new EmailCollectorMessage(emailFound);
        queue.add(collectorMessage);

        logger.info("Email found ---> " + emailFound);
    }
}