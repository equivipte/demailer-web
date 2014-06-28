package com.equivi.demailer.service.emailcollector;

import org.springframework.web.context.request.async.DeferredResult;

import com.equivi.demailer.dto.emailer.EmailCollectorMessage;

public interface EmailCollectorService {
	void subscribe(String site) throws Exception;
	
	void getUpdate(DeferredResult<EmailCollectorMessage> deferredResult);
}
