package com.equivi.mailsy.util;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
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

import static com.equivi.mailsy.util.EmailCrawlingUtil.PATTERN_MAIL_FILTERS;
import static com.equivi.mailsy.util.EmailCrawlingUtil.PATTERN_MAIL;

public class EmailCrawler extends WebCrawler {

	public static final BlockingQueue<EmailCollectorMessage> queue = new LinkedBlockingQueue<>();
    public static final BlockingQueue<EmailCollectorUrlMessage> urlQueue = new LinkedBlockingQueue<>();

	private String[] crawlDomains;

	@Override
	public void onStart() {
		crawlDomains = (String[]) myController.getCustomData();
	}

	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (PATTERN_MAIL_FILTERS.matcher(href).matches()) {
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
	    Matcher m = PATTERN_MAIL.matcher(doc.text());

	    while(m.find()) {
			if(passKeywordFilter(m.group(0))) {
            	addIntoQueue(m);
			}
	    }

        // find email from links
        for (Element link : links) {
            m = PATTERN_MAIL.matcher(link.attr("href"));
            while(m.find()) {
				if(passKeywordFilter(m.group(0))) {
					addIntoQueue(m);
				}
            }
        }
	}

    private void addIntoQueue(Matcher m) {
        String emailFound = m.group(0);
        EmailCollectorMessage collectorMessage = new EmailCollectorMessage(emailFound);
        queue.add(collectorMessage);

        logger.info("Email found ---> " + emailFound);
    }

	private boolean passKeywordFilter(String email) {
		return email.matches(EmailCrawlingUtil.REGEX_MAIL_KEYWORD);
	}
}