package com.qa.contacts.api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthTypes;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsTest extends BaseTest{
	

	private String tokenId;
	
	@BeforeMethod
	public void getToken() {
		
		ContactsCredentials creds = ContactsCredentials.builder()
							.email("naveenanimation20@gmail.com")
							.password("test@123")
							.build();
		
		Response response = restclient.post(BASE_URL_CONTACTS,"/users/login", creds, null, null, AuthTypes.No_Auth, ContentType.JSON);
		tokenId = response.jsonPath().getString("token");
		System.out.println("Token id ===>" + tokenId);
		ConfigManager.set("contacts_bearer_Token", tokenId);
	}
	
	
	@Test
	public void getContactsTest() {
		Response response = restclient.get( BASE_URL_CONTACTS,"/contacts", null, null, AuthTypes.CONTACTS_BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
	}
}
