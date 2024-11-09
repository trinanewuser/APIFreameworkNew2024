package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;

import io.restassured.http.ContentType;

public class GetUserTest extends BaseTest {

	@Test
	public void getUsers() {
		Map<String,String> queryparams=new HashMap<String,String>();
		queryparams.put("name","naveen");
		queryparams.put("status","active");
		Response res=restclient.get(BASE_URL_GOREST,"public/v2/users", queryparams, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(res.getStatusCode(),200);
	}
	
	@Test(enabled=false)
	public void getSingleUser() {
		
		Response res=restclient.get(BASE_URL_GOREST,"public/v2/users/7466358",null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(res.getStatusCode(),200);
	}
}
