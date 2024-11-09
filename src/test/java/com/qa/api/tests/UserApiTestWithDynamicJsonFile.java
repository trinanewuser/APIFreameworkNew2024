package com.qa.api.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserApiTestWithDynamicJsonFile extends BaseTest {
	
	@Test
	public void createUserWithJsonFileTest() {
		String jsonfilepath="src\\test\\resources\\jsons\\user.json";
		ObjectMapper mapper=new ObjectMapper();
		try {
			JsonNode usernode=mapper.readTree(Files.readAllBytes(Paths.get(jsonfilepath)));
			
			//create a unique Email id
			String uniqueEmailId=StringUtility.getRandomEamilId();
			
			//Update email id
			ObjectNode objnode=((ObjectNode)usernode);
			objnode.put("email",uniqueEmailId);
			//convert JsonNode to jsonString
			String updateJsonString=mapper.writeValueAsString(usernode);
			System.out.println("updated json String==="+updateJsonString);
			Response res=restclient.post(BASE_URL_GOREST,"public/v2/users",updateJsonString,null, null,AuthTypes.Bearer_Token, ContentType.JSON);
			Assert.assertEquals(res.getStatusCode(),201);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
