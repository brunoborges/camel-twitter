package org.apache.camel.component.twitter.data;

import java.util.Date;

public class Status {

	private Date date;
	private User user;
	private long id;
	private String text;
	private boolean truncated;

	public Status(twitter4j.Status s, User user) {
		this.user = user;
		this.id = s.getId();
		this.text = s.getText();
		this.date = s.getCreatedAt();
		this.truncated = s.isTruncated();
	}

	public Status(User user, long statusId, String text, Date date, boolean truncated) {
		this.user = user;
		this.id = statusId;
		this.text = text;
		this.date = date;
		this.truncated = truncated;
	}

	public Date getDate() {
		return date;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getDate()).append(" (").append(getUser().getScreenname())
				.append(") ");
		s.append(getText());
		return s.toString();
	}
}
