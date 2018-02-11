package com.javacircle.batch.feed.domain;

public class StudentDepart {

	private String firstName;
	private String deptName;

	public StudentDepart() {

	}

	public StudentDepart(String firstName, String deptName) {
		super();
		this.firstName = firstName;
		this.deptName = deptName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
