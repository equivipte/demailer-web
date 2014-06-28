package com.equivi.mailsy.service.emailcollector;

import org.springframework.web.context.request.async.DeferredResult;

import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;

public interface EmailCollectorService {
	void subscribe(String site) throws Exception;
	
	void getUpdate(DeferredResult<EmailCollectorMessage> deferredResult);
}
