package apiTesting.DemoProject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import apiTesting.pojos.Place;
import apiTesting.utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Unit test for simple App.
 */
public class SpecBuilderTest {
	String placeID;

	@Test()
	public void addPlaceTest() throws IOException {
		// Read json file ---> convert into Bytes->String
		// Given - all input details

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK)
				.expectContentType(ContentType.JSON).build();
		// JSON from file to Object
		Place requestBody = ReusableMethods.jsonToPOJOMapper(
				System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\addPlace.json", Place.class);
		// When - Submit the API
		// Then - Validate the response
		
		  RequestSpecification res=given().spec(requestSpecification)
		  .body(requestBody); 
		  
		  String responseData = res
		  .when().post("/maps/api/place/add/json")
		  .then().spec(responseSpecification).body("scope", equalTo("APP"))
		  .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		 
		  System.out.println(responseData);
	}

	//@Test
	public void test1() {

		// JSON from file to Object
				Place p = ReusableMethods.jsonToPOJOMapper(
						System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\addPlace.json", Place.class);
		
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();

		
		ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification res=given().spec(req)
		.body(p);

		Response response =res.when().post("/maps/api/place/add/json").
		then().spec(resspec).extract().response();

		String responseString=response.asString();
		System.out.println(responseString);
		

	}

	// @Test(dependsOnMethods = "addPlaceTest")
	public void updatePlaceTest() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().queryParam("key", "qaclick123").body(Payload.updatePlace(placeID)).when()
				.put("/maps/api/place/update/json").then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
	}

	// @Test(dependsOnMethods = "updatePlaceTest")
	public void getPlace() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String responseData = given().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.header("Content-Type", "text/json").when().get("/maps/api/place/get/json").then().log().all()
				.assertThat().statusCode(200).extract().asString();

		JsonPath responseJson = ReusableMethods.rawToJSon(responseData);

		assertEquals(responseJson.getString("address"), "70 winter walk, USA");
		;
	}
}
