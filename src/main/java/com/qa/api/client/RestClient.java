package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import com.qa.api.constants.AuthTypes;
import com.qa.api.excption.FrameworkException;
import com.qa.api.manager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class RestClient {

	private ResponseSpecification response200 = expect().statusCode(200);
	private ResponseSpecification response200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	private ResponseSpecification response201or200 = expect().statusCode(anyOf(equalTo(201), equalTo(200)));
	private ResponseSpecification response204 = expect().statusCode(204);
	private ResponseSpecification response400 = expect().statusCode(400);
	private ResponseSpecification response404 = expect().statusCode(404);
	private ResponseSpecification response422 = expect().statusCode(422);
	private ResponseSpecification response500 = expect().statusCode(500);
	private ResponseSpecification response501 = expect().statusCode(501);

	private String baseUrl = ConfigManager.get("baseUrl");

	private RequestSpecification setRequest(String baseUrl, AuthTypes authtype, ContentType contentType) {

		RequestSpecification request = RestAssured.given().baseUri(baseUrl).contentType(contentType)
				.accept(contentType);

		switch (authtype) {
		case Bearer_Token:
			request.header("Authorization", "Bearer " + ConfigManager.get("beareToken"));
			break;
		case CONTACTS_BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("contacts_bearer_Token"));
			break;
		case OAuth2:
			request.header("Authorization", "Bearer " + ConfigManager.get("beareToken"));
			break;
		case Basic_Auth:
			request.header("Authorization", "Basic " + generateBasicAuth());
			break;
		case Api_Key:
			request.header("x-api-key", ConfigManager.get("Api_Key"));
			break;

		case No_Auth:
			System.out.println("Auth is not required....");
			;
			break;
		default:
			System.out.println("This Auth is not supported...please pass righr auth Type");
			;
			new FrameworkException("No Auth Supported");
		}

		return request;
	}

	private String generateBasicAuth() {
		String credentials = ConfigManager.get("basicUsername") + ":" + ConfigManager.get("basicPassword");
		return Base64.getEncoder().encodeToString(credentials.getBytes());

	}

	private String generateOAuth2Token() {
		return RestAssured.given().formParam("client_id", ConfigManager.get("clientId"))
				.formParam("client_secret", ConfigManager.get("clientSecret"))
				.formParam("grant_type", ConfigManager.get("grantType")).post(ConfigManager.get("tokenUrl")).then()
				.extract().path("access_token");

	}
	// *********Crude operation*******//

	/**
	 * this method used to GET APIS
	 * 
	 * @param endpoint
	 * @param queryparam
	 * @param pathparam
	 * @param authtype
	 * @param contentType
	 * @return it returns get API response
	 */
	/*
	 * public Response get(String baseUrl, String endpoint, Map<String, String>
	 * queryparam, Map<String, String> pathparam, AuthTypes authtype, ContentType
	 * contentType) {
	 * 
	 * RequestSpecification request = setRequest(baseUrl, authtype, contentType);
	 * 
	 * setQueryAndPathParam(request, queryparam, pathparam); Response response =
	 * request.get(endpoint).then().log().all().spec(response200or404).extract().
	 * response(); response.prettyPrint(); return response; }
	 */

	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthTypes authType, ContentType contentType) {

		RequestSpecification request = setRequest(baseUrl, authType, contentType);

		 setQueryAndPathParam(request, queryParams, pathParams);

		Response response = request.get(endPoint).then().log().all().spec(response200or404).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * this method used to POST APIS
	 * 
	 * @param endpoint
	 * @param queryparam
	 * @param pathparam
	 * @param authtype
	 * @param contentType
	 * @return it returns post API response
	 */
	public <T> Response post(String baseUrl, String endpoint, T body, Map<String, String> queryparam,
			Map<String, String> pathparam, AuthTypes authtype, ContentType contentType) {
		RequestSpecification request = setRequest(baseUrl, authtype, contentType);

		setQueryAndPathParam(request, queryparam, pathparam);
		Response response = request.body(body).post(endpoint).then().spec(response201or200).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * this method used to POST APIS using file body(overloading post method)
	 * 
	 * @param endpoint
	 * @param queryparam
	 * @param pathparam
	 * @param authtype
	 * @param contentType
	 * @return it returns post API response
	 */
	public <T> Response post(String baseUrl, String endpoint, File body, Map<String, String> queryparam,
			Map<String, String> pathparam, AuthTypes authtype, ContentType contentType) {
		RequestSpecification request = setRequest(baseUrl, authtype, contentType);

		setQueryAndPathParam(request, queryparam, pathparam);
		Response response = request.body(body).post(endpoint).then().spec(response201or200).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * this method used to PATCH APIS using file body(overloading post method)
	 * 
	 * @param endpoint
	 * @param queryparam
	 * @param pathparam
	 * @param authtype
	 * @param contentType
	 * @return it returns patch API response
	 */
	public <T> Response patch(String baseUrl, String endpoint, T body, Map<String, String> queryparam,
			Map<String, String> pathparam, AuthTypes authtype, ContentType contentType) {
		RequestSpecification request = setRequest(baseUrl, authtype, contentType);

		setQueryAndPathParam(request, queryparam, pathparam);
		Response response = request.body(body).patch(endpoint).then().spec(response200).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * this method used to PUT APIS using file body(overloading post method)
	 * 
	 * @param endpoint
	 * @param queryparam
	 * @param pathparam
	 * @param authtype
	 * @param contentType
	 * @return it returns put API response
	 */
	public <T> Response put(String baseUrl, String endpoint, T body, Map<String, String> queryparam,
			Map<String, String> pathparam, AuthTypes authtype, ContentType contentType) {
		RequestSpecification request = setRequest(baseUrl, authtype, contentType);

		setQueryAndPathParam(request, queryparam, pathparam);
		Response response = request.body(body).put(endpoint).then().spec(response200).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * this method used to DELETE APIS using file body(overloading post method)
	 * 
	 * @param endpoint
	 * @param queryparam
	 * @param pathparam
	 * @param authtype
	 * @param contentType
	 * @return it returns delete API response
	 */
	public <T> Response delete(String baseUrl, String endpoint, Map<String, String> queryparam,
			Map<String, String> pathparam, AuthTypes authtype, ContentType contentType) {
		RequestSpecification request = setRequest(baseUrl, authtype, contentType);

		setQueryAndPathParam(request, queryparam, pathparam);
		Response response = request.delete(endpoint).then().spec(response204).extract().response();
		response.prettyPrint();
		return response;
	}

	private void setQueryAndPathParam(RequestSpecification request, Map<String, String> queryparam,
			Map<String, String> pathparam) {
		if (queryparam != null) {
			request.queryParams(queryparam);
		}
		if (pathparam != null) {
			request.pathParams(pathparam);
		}
	}

}
