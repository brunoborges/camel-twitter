package org.apache.camel.component.twitter;

import java.util.Date;

public interface Status {

	public long getId();

	public String getText();

	public boolean isTruncated();

	public User getUser();

	public Date getDate();
}
