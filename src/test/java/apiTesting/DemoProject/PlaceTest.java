package apiTesting.DemoProject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import apiTesting.pojos.Place;
import apiTesting.utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
/**
 * Unit test for simple App.
 */
public class PlaceTest 
{
	String placeID;

	@Test()
	public void addPlaceTest() throws IOException {
		//Read json file ---> convert into Bytes->String
		//Given - all input details
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//JSON from file to Object
		Place requestBody = ReusableMethods.jsonToPOJOMapper(System.getProperty("user.dir")+"\\src\\main\\resources\\jsonFiles\\addPlace.json", Place.class);
		//When - Submit the API
		//Then - Validate the response
		String responseData = given().queryParam("key", "qaclick123").header("Content-Type","text/json")
		//.body(Payload.addPlace())
		//.body(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\resources\\jsonFiles\\addPlace.json")))
		.body(requestBody)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		JsonPath jsonResponse =  ReusableMethods.rawToJSon(responseData);
		placeID = jsonResponse.getString("place_id");
		System.out.println(placeID);
	}
	
	//@Test(dependsOnMethods = "addPlaceTest")
	public void updatePlaceTest() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().queryParam("key", "qaclick123")
		.body(Payload.updatePlace(placeID))
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
	}
	
	//@Test(dependsOnMethods = "updatePlaceTest")
	public void getPlace() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String responseData = given().queryParam("key", "qaclick123").queryParam("place_id", placeID).header("Content-Type","text/json")
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath responseJson =  ReusableMethods.rawToJSon(responseData);

		assertEquals(responseJson.getString("address"),"70 winter walk, USA");;
	}
}
