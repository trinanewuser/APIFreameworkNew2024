package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.pojo.Users;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{
	
	@Test
	public void deleteUserTestWithBuilder() {
		
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
		Assert.assertEquals(resget.getStatusCode(),200);
		Assert.assertEquals(resget.jsonPath().getString("id"),userid);
		Assert.assertEquals(resget.jsonPath().getString("name"),user.getName());
		Assert.assertEquals(resget.jsonPath().getString("email"),user.getEmail());
		
		//3. DELETE: update the same user using the same user id
				Response responseDelete = restclient.delete(BASE_URL_GOREST,"/public/v2/users/"+userid, null, null, AuthTypes.Bearer_Token, ContentType.JSON);
				Assert.assertEquals(responseDelete.getStatusCode(), 204);
				
		//4. GET: recheck and fetch the user with the same user id
				Response responseGetAfterDelete = restclient.get(BASE_URL_GOREST,"/public/v2/users/"+userid, null, null, AuthTypes.Bearer_Token, ContentType.JSON);
				Assert.assertEquals(responseGetAfterDelete.getStatusCode(), 404);
				Assert.assertEquals(responseGetAfterDelete.jsonPath().getString("message"), "Resource not found");
	}

}
