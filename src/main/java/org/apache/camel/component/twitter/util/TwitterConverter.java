package org.apache.camel.component.twitter.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Converter;
import org.apache.camel.component.twitter.data.Status;

@Converter
public class TwitterConverter {

	private TwitterConverter() {
		// Helper class
	}

	@Converter
	public static String toString(Status status) throws ParseException {
		return status.toString();
	}
	
	public static Status convertStatus(twitter4j.Status s) {
		return new Status(s);
	}
	
	public static List<Status> convertStatuses(List<twitter4j.Status> ls) {
		List<Status> newLs = new ArrayList<Status>(ls.size());
		for (Iterator<twitter4j.Status> i = ls.iterator(); i.hasNext();) {
			newLs.add(convertStatus(i.next()));
		}
		return newLs;
	}
}
