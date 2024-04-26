package apiTesting.auth;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * WebDriver driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); driver.findElement(By.cssSelector("[type='email']")).sendKeys(
		 * "jayaseo1990@gmail.com");
		 * driver.findElement(By.xpath("//*[contains(text(), \"Next\")]/parent::button")
		 * ).click(); Thread.sleep(3000);
		 * driver.findElement(By.cssSelector("[type='password']")).sendKeys("Jaya@123");
		 * driver.findElement(By.cssSelector("[type='email']")).sendKeys(Keys.ENTER);
		 * String url = driver.getCurrentUrl();
		 */
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AeaYSHAIpQO8SXDLCn2du7F9Wc5PmifxNWzHiCxlTWQpLxq14xIm1xs45qrBr1jM3fa64w&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode =url.split("code=")[1];
		String code = partialCode.split("&scope=")[0];
		System.out.println(code);
		String accessCodeResponse =given().log().all().header("Content-Type", "application/json").urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().extract().asString();
		
		JsonPath jsonPath = new JsonPath(accessCodeResponse);
		String responseData = given().log().all().queryParam("access_token", jsonPath.getString("access_token"))
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().extract().response().asString();
		System.out.println(responseData);
		jsonPath = new JsonPath(responseData);

	}

}
