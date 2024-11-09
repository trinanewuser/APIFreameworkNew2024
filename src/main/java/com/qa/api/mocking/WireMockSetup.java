package com.qa.api.mocking;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class WireMockSetup {
	private static WireMockServer server;
	
	public static void mockServerStart() {
		server=new WireMockServer(8088);
		WireMock.configureFor("localhost",8088);
		server.start();
	}
	
	public static void mockServerStop() {
		server.stop();
	}

}
