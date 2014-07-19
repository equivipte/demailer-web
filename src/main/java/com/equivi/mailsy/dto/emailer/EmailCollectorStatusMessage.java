package com.equivi.mailsy.dto.emailer;

public class EmailCollectorStatusMessage {
	private boolean status;
	
	public EmailCollectorStatusMessage(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
