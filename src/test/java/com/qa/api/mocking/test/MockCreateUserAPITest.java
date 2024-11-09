package com.qa.api.mocking.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.mocking.ApiMocking;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;
public class MockCreateUserAPITest extends BaseTest{
	
	@Test
	public void createDummyUserTest() {
		ApiMocking.createDummyUser();
		
		
		String dummyJson = "{\"name\": \"Tom\"}";
		Response response = restclient.post(BASE_URL_LOCALHOST_PORT, "/api/users", dummyJson, null, null, AuthTypes.No_Auth, ContentType.JSON);
		response.then()
			.assertThat()
				.statusCode(201)
					.statusLine(equalTo("HTTP/1.1 201 user is created"))
					.body("id", equalTo(1));
		
	}
	
	
	@Test
	public void createDummyUserWithJsonFileTest() {
		ApiMocking.createDummyUserWithJsonFile();
		
		String dummyJson = "{\"name\": \"api\"}";
		Response response = restclient.post(BASE_URL_LOCALHOST_PORT, "/api/users", dummyJson, null, null, AuthTypes.No_Auth, ContentType.JSON);
		response.then()
			.assertThat()
				.statusCode(201)
					.statusLine(equalTo("HTTP/1.1 201 user is created"))
					.body("id", equalTo(101));
		
	}

}
