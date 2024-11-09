package com.qa.api.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;

import com.qa.api.constants.AuthTypes;
import com.qa.api.pojo.Users;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	@DataProvider
	public Object[][] getUserData(){
		return new Object[][] {
			{"Naveen","male","active"},
			{"Mukesh","male","active"},
			{"Sanjay","male","Inactive"}
		};
	}
	
	@Test(dataProvider="getUserData")
	public void createUserTest(String name,String gender,String status) {
		Users user=new Users(name,gender,StringUtility.getRandomEamilId(),status);
		Response res=restclient.post(BASE_URL_GOREST,"public/v2/users",user,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(res.getStatusCode(),201);
	}
	
	@Test(dataProvider="getUserData")
	public void createUserTestWithBuilder(String name,String gender,String status) {
		
		//post
		Users user=Users.builder()
				.name(name)
				.email(StringUtility.getRandomEamilId())	
				.status(status)
				.gender(gender)
				.build();
		Response res=restclient.post(BASE_URL_GOREST,"public/v2/users",user,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(res.getStatusCode(),201);
		String userid=res.jsonPath().getString("id");
		System.out.println("userId: "+userid);
		
		//get
		Response resget=restclient.get(BASE_URL_GOREST,"public/v2/users/"+userid,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(resget.getStatusCode(),200);
		Assert.assertEquals(resget.jsonPath().getString("id"),userid);
		Assert.assertEquals(resget.jsonPath().getString("name"),user.getName());
		Assert.assertEquals(resget.jsonPath().getString("email"),user.getEmail());
	}
	
	@Test(enabled=false)
	public void createUserTestWithFile() {
		File file=new File("./src/test/resources/jsons/user.json");
		Response res=restclient.post(BASE_URL_GOREST,"public/v2/users",file,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(res.getStatusCode(),201);
	}

}
