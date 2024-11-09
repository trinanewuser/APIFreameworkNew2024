package com.qa.api.amadeus.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest {
	
	@Test()
	public void getFlightDetailsTest() {
		
				
		Map<String, String> queryParams = Map.of("origin", "PAR", "maxPrice", "200");
		Response response = restclient.get(BASE_URL_AMADEUS, "/v1/shopping/flight-destinations", queryParams, null, AuthTypes.OAuth2, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
