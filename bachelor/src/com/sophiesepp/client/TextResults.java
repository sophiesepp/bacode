package com.sophiesepp.client;

import com.google.gwt.core.client.GWT;
import com.sophiesepp.client.GreetingService;
import com.sophiesepp.client.GreetingServiceAsync;

public class TextResults {

	protected final static GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
}
