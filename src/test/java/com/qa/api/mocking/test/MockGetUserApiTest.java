package com.qa.api.mocking.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.mocking.ApiMocking;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.Test;
public class MockGetUserApiTest extends BaseTest {
	
	@Test
	public void getDummyUserTest() {
		
		ApiMocking.getDummyUser();		
		Response response = restclient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthTypes.No_Auth, ContentType.ANY);
		response.then()
					.assertThat()
						.statusCode(200)
							.body("name", equalTo("Tom"));
	}
	
	@Test
	public void getDummyUserWithQueryParamTest() {
		
		ApiMocking.getDummyUserWithQueryParams();
		Map<String, String> queryParams = Map.of("name","Tom");
		Response response = restclient.get(BASE_URL_LOCALHOST_PORT, "/api/users", queryParams, null, AuthTypes.No_Auth, ContentType.ANY);
		response.then()
					.assertThat()
						.statusCode(200)
							.body("name", equalTo("Tom"));
	}
	
	
	
	@Test
	public void getDummyUserWithJsonFileTest() {
		
		ApiMocking.getDummyUserWithJsonFile();		
		Response response = restclient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthTypes.No_Auth, ContentType.ANY);
		response.then()
					.assertThat()
						.statusCode(200)
							.body("name", equalTo("Tom"));
	}

}
