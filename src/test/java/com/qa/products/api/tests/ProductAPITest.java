package com.qa.products.api.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest {
	
	@Test
	public void getProductsAPITest() {
		Response response = restclient.get(BASE_URL_PRODUCT,"/products", null, null, AuthTypes.No_Auth, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	
	@Test
	public void getProductsAPILimitTest() {
		
		Map<String,String> queryParam = Map.of("limit", "5");
		Response response = restclient.get(BASE_URL_PRODUCT,"/products", queryParam, null, AuthTypes.No_Auth, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
	}

}
