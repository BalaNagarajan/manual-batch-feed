package com.javacircle.batch.feed.domain;

public class Person {
	private String lastName;

	private String firstName;
	private String email;
	private String age;
	private String deptCode;
	
	public Person() {

	}

	public Person(String firstName, String lastName, String email, String age, String deptCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
		this.deptCode = deptCode;
	
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	
	

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", age=" + age
				+ ", deptCode=" + deptCode + "]";
	}

}
