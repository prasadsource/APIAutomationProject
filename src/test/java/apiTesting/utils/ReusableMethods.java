package apiTesting.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

	public static JsonPath rawToJSon(String response) {
		JsonPath responseJson = new JsonPath(response);
		return responseJson;
	}
	
	public static byte[] dataFromJSON(String fileName) {
		try {
			return Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\resources\\jsonFiles\\"+fileName+""));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public static<T>  T jsonToPOJOMapper(String filePath,Class<T> convert) {
		T pojo = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			pojo = mapper.readValue(new File(filePath), convert);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return pojo;
	}
}
