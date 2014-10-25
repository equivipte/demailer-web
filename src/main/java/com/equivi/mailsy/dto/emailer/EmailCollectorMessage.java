package com.equivi.mailsy.dto.emailer;

import java.util.List;
import java.util.Objects;

public class EmailCollectorMessage {
    private List<String> emails;
	private String email;

    public EmailCollectorMessage() {
    }

    public EmailCollectorMessage(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
	public int hashCode() {
		return Objects.hash(this.email);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		final EmailCollectorMessage other = (EmailCollectorMessage) obj;
		return Objects.equals(this.email.toLowerCase(), other.email.toLowerCase());
	}

}
