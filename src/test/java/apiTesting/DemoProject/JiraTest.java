package apiTesting.DemoProject;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import apiTesting.utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	SessionFilter session = new SessionFilter();
	Map<String, Object> map = new HashMap<>();

	@BeforeTest
	public void loginTest() {

		RestAssured.baseURI = "http://localhost:9090";
		given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.dataFromJSON("\\jiraData\\loginCredentials.json")).filter(session)
				.when().post("/rest/auth/1/session").
				then().statusCode(HttpStatus.SC_OK).extract().response().asString();

	}

	//@Test
	public void createIssue() {
		String responseData = given().filter(session).header("Content-Type", "application/json")
				.body(ReusableMethods.dataFromJSON("\\\\jiraData\\createIssue.json"))

				.when().post("/rest/api/2/issue")

				.then().assertThat().statusCode(HttpStatus.SC_CREATED).extract().response().asString();

		JsonPath jsonPath = ReusableMethods.rawToJSon(responseData);
		System.out.println(jsonPath.getInt("id"));
		map.put("issueIdOrKey", jsonPath.getInt("id"));
	}

	//@Test(/* dependsOnMethods = "createIssue" */)
	public void addComment() {
		//assertTrue(map.get("issueIdOrKey")!=null);
		String responseData = given().filter(session).header("Content-Type", "application/json")
				.pathParam("issueIdOrKey",/*map.get("issueIdOrKey")*/10111)
				.body(ReusableMethods.dataFromJSON("\\jiraData\\addComment.json"))

				.when().post("/rest/api/2/issue/{issueIdOrKey}/comment")

				.then().assertThat().statusCode(HttpStatus.SC_CREATED).extract().response().asString();

		JsonPath jsonPath = ReusableMethods.rawToJSon(responseData);
		System.out.println(jsonPath.getInt("id"));
		map.put("commentID", jsonPath.getInt("id"));
	}
	
	//@Test(dependsOnMethods = "createIssue")
	public void addAttachement() {
		assertTrue(map.get("issueIdOrKey")!=null);
		
		given().header("X-Atlassian-Token", "no-check")
		.filter(session).pathParam("issueIdOrKey",map.get("issueIdOrKey"))
		.header("Content-Type", "multipart/form-data")
		.multiPart("file",new File(System.getProperty("user.dir")+"\\src\\main\\resources\\jsonFiles\\jiraData\\attachement1.txt"))
		
		.when().post("/rest/api/2/issue/{issueIdOrKey}/attachments")
		.then().statusCode(HttpStatus.SC_OK);
	}
	
	@Test(/* dependsOnMethods = "createIssue" */)
	public void getIssue() {
		//assertTrue(map.get("issueIdOrKey")!=null);
		
		String responseData = given().filter(session).pathParam("issueIdOrKey",/*map.get("issueIdOrKey")*/10111)
		.queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{issueIdOrKey}")
				.then()/* .log().all() */.statusCode(HttpStatus.SC_OK).extract().response().asString();
		
		JsonPath jsonPath = ReusableMethods.rawToJSon(responseData);
		int commentArraySize = jsonPath.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentArraySize; i++) {
			
			if(jsonPath.getInt("fields.comment.comments["+i+"].id")==10003) {
				
				System.out.print( jsonPath.getInt("fields.comment.comments["+i+"].id")+" ");;
				System.out.println( jsonPath.getString("fields.comment.comments["+i+"].body"));;
			}
			
		}
	}

}
