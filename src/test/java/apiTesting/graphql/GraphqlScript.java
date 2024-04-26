package apiTesting.graphql;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class GraphqlScript {

	@Test()
	public void addPlaceTest() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String responseData = given().log().all().header("Content-Type", "application/json").body(
				"{\"query\":\"mutation($locationName:String!,$characterName:String!){\\n  createLocation(location:{\\n    name:$locationName,type:\\\"Residence\\\",dimension:\\\"123\\\"\\n    \\n  }){\\n    id\\n  }\\n  \\n createCharacter(character: {name: $characterName, type: \\\"Cool\\\", status: \\\"Alive\\\", species: \\\"Sapiens\\\", gender: \\\"Male\\\", image: \\\"png\\\", originId: 80, locationId: 8596}) {\\n    id\\n  }\\n  createEpisode(episode:{\\n    name:\\\"Horror\\\",\\n    air_date:\\\"date1\\\",\\n    episode:\\\"20\\\"\\n  }){\\n    id\\n  }\\n  deleteLocations(locationIds:[8590]){\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"locationName\":\"Chennai\",\"characterName\":\"Ramesh\"}}")
				.when().post("/gq/graphql").then().extract().asString();
		System.out.println(responseData);
		JsonPath jsonPath = new JsonPath(responseData);
		System.out.println(jsonPath);

		// mutations
		String createResponseData = given().log().all().header("Content-Type", "application/json").body(
				"{\"query\":\"mutation($locationName:String!){\\n  createLocation(location:{\\n    name:$locationName,type:\\\"Residence\\\",dimension:\\\"123\\\"\\n    \\n  }){\\n    id\\n  }\\n}\",\"variables\":{\"locationName\":\"Chennai\"}}")
				.when().post("/gq/graphql").then().extract().asString();
		jsonPath = new JsonPath(createResponseData);
		System.out.println(jsonPath);
	}
}
