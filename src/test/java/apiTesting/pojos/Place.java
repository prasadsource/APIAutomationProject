package apiTesting.pojos;

import java.util.Arrays;

public class Place {

	private Location location;
	private Integer accuracy;
	private String name;
	private String phone_number;
	private String address;
	private String[] types;
	private String website;
	private String language;
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public String toString() {
		return "Place [location=" + location + ", accuracy=" + accuracy + ", name=" + name + ", phone_number="
				+ phone_number + ", address=" + address + ", types=" + Arrays.toString(types) + ", website=" + website
				+ ", language=" + language + "]";
	} 
	
	
}
