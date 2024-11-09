package com.qa.api.mocking.test;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.mocking.ApiMocking;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockGetProductApiTest extends BaseTest {
	
	@Test
	public void getDummyUserWithJsonFileTest() {
		
		ApiMocking.getDummyProductsWithJsonFile();		
		Response response = restclient.get(BASE_URL_LOCALHOST_PORT, "/api/products", null, null, AuthTypes.No_Auth, ContentType.ANY);
		response.then()
					.assertThat()
						.statusCode(200)
							.body("name", equalTo("api"));
	}

}
