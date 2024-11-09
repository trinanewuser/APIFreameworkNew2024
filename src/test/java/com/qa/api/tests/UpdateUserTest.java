package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthTypes;
import com.qa.api.pojo.Users;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest{
	@DataProvider
	public Object[][] getUserData(){
		return new Object[][] {
			{"Naveen","male","active","inactive","NaveenAutomation"},
			{"Mukesh","male","active","inactive","MukeshAutomation"},
			{"Sanjay","male","Inactive","active","Sanjayautomation"}
		};
	}
	@Test(dataProvider="getUserData")
	public void updateUserTestWithBuilder(String name,String gender,String status,String updatedStatus,String updatedName) {
		
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
		

		//update the user details using the setter:
		user.setStatus(updatedStatus);
		user.setName(updatedName);
		
		//3. PUT: update the same user using the same user id
		Response responsePUT = restclient.put(BASE_URL_GOREST,"/public/v2/users/"+userid, user, null, null, AuthTypes.Bearer_Token, ContentType.JSON);
		Assert.assertEquals(responsePUT.getStatusCode(), 200);
		Assert.assertEquals(responsePUT.jsonPath().getString("id"), userid);
		Assert.assertEquals(responsePUT.jsonPath().getString("status"), user.getStatus());
		Assert.assertEquals(responsePUT.jsonPath().getString("name"), user.getName());
		
		}
	

}
