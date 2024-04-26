package apiTesting.dtos;

import java.io.Serializable;

public class Course implements Serializable{
	
	String title;
	Double price;
	Integer copies;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	@Override
	public String toString() {
		return "Course [title=" + title + ", price=" + price + ", copies=" + copies + "]";
	}
	
	

}
