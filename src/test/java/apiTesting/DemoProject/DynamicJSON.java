package apiTesting.DemoProject;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import apiTesting.utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

public class DynamicJSON {
	
	String bookID;
	
	@Test(dataProvider = "getData")
	public void addBook(String name,String isbn,int aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String responseData = given().header("Content-Type", "text/json").body(Payload.addBook(name, isbn, aisle)) 
		.when().post("/Library/Addbook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath jsonPath = ReusableMethods.rawToJSon(responseData);
		assertEquals(jsonPath.getString("Msg"), "successfully added");
		bookID = jsonPath.getString("ID");
		
	}
	
	@DataProvider
	public Object[][] getData(){
		Object[][] array = new Object[2][];
		array[0] = new Object[]{"Java 11 V1","Software11.0",1100};
		array[1] = new Object[]{"Java 11 V2","Software11.1",1101};
		return array;
	}

}
