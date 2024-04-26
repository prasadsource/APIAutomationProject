package apiTesting.DemoProject;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import apiTesting.dtos.Course;
import apiTesting.utils.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class CourseTest {

	@Test
	public void printNoOfCourses(){
		JsonPath jsonPath = ReusableMethods.rawToJSon(Payload.coursesMockData());
		int NoOfCourses = jsonPath.getInt("courses.size()");
		System.out.println(NoOfCourses);
		
		int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//First Course title
		String firstCourseTitle = jsonPath.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		
		/*
		 * List<Course> courses = jsonPath.getList("courses", Course.class);
		 * courses.forEach(System.out::println);
		 */
		
		for (int i = 0; i < NoOfCourses; i++) {
			System.out.println(String.format("Title:%s Price :%d",  jsonPath.getString("courses["+i+"].title"),jsonPath.getInt("courses["+i+"].price")));
		}
		
		//No of Copies sold by RPA Course
		for (int i = 0; i < NoOfCourses; i++) {
			if("RPA".equals(jsonPath.getString("courses["+i+"].title"))) {
				System.out.println(String.format("No of Copies for RPA :%d",jsonPath.getInt("courses["+i+"].copies")));
			}
		}
		
		int sum = 0;
		//No of Copies sold by RPA Course
		for (int i = 0; i < NoOfCourses; i++) {
			int coursePrice = jsonPath.getInt("courses["+i+"].price") * jsonPath.getInt("courses["+i+"].copies");
			sum = sum+coursePrice;
		}
		System.out.println(sum);
		assertEquals(sum, purchaseAmount);
		
		
	}
}
