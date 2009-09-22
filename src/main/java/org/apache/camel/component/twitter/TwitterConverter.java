package org.apache.camel.component.twitter;

import java.text.ParseException;

import org.apache.camel.Converter;

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
