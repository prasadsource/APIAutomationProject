package apiTesting.pojos;

import java.util.List;
import java.util.Map;

public class GetCourse {

	private String url;
	private String instructor;
	private String services;
	private String expertise;
	private String linkedIn;
	private Map<String,List<Course>> courses;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	public Map<String, List<Course>> getCourses() {
		return courses;
	}
	public void setCourses(Map<String, List<Course>> courses) {
		this.courses = courses;
	}
	@Override
	public String toString() {
		return "GetCourse [url=" + url + ", instructor=" + instructor + ", services=" + services + ", expertise="
				+ expertise + ", linkedIn=" + linkedIn + ", courses=" + courses + "]";
	}	
}
