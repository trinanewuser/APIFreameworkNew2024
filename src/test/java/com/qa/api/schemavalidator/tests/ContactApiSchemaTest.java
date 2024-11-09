package com.qa.api.schemavalidator.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.pojo.Users;
import com.qa.api.utils.JsonSchemaUtils;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactApiSchemaTest extends BaseTest{
	
	@Test
	public void createUserTestWithBuilder() {
		
		//post
		Users user=Users.builder()
				.name("APiName")
				.email(StringUtility.getRandomEamilId())	
				.status("active")
				.gender("female")
				.build();
		Response res=restclient.post(BASE_URL_GOREST,"public/v2/users",user,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(res.getStatusCode(),201);
		String userid=res.jsonPath().getString("id");
		System.out.println("userId: "+userid);
		
		//get
		Response resget=restclient.get(BASE_URL_GOREST,"public/v2/users/"+userid,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(JsonSchemaUtils.validateSchema(resget, "schemas/contact_schema.json"),true);
	}

}
