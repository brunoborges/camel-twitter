package org.apache.camel.component.twitter.util;

import java.text.ParseException;

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

}
