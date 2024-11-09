package com.qa.api.schemavalidator.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.utils.JsonSchemaUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductApiSchemaTest extends BaseTest{
	
	@Test
	public void getProductsAPISchemaTest() {
		Response response = restclient.get(BASE_URL_PRODUCT,"/products", null, null, AuthTypes.No_Auth, ContentType.JSON);
		Assert.assertEquals(JsonSchemaUtils.validateSchema(response, "schemas/product_schema.json"),true);
		
	}

}
