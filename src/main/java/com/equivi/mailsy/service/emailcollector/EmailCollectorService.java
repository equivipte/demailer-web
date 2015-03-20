package com.equivi.mailsy.service.emailcollector;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import org.springframework.web.context.request.async.DeferredResult;

public interface EmailCollectorService {
	void subscribe(String site) throws Exception;
	
	boolean getUpdateCrawlingStatus();

    void cancel();

}
