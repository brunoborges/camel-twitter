package org.apache.camel.component.twitter;

import java.util.Date;

class StatusImpl implements Status {

	private Date date;
	private User user;
	private long id;
	private String text;
	private boolean truncated;

	protected StatusImpl(twitter4j.Status s, User user) {
		this.user = user;
		this.id = s.getId();
		this.text = s.getText();
		this.date = s.getCreatedAt();
		this.truncated = s.isTruncated();
	}

	protected StatusImpl(User user, long statusId, String text, Date date, boolean truncated) {
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
