package com.qa.trcrm.pojo;

public class Contacts {

	String firstName;
	String emailId;

	public Contacts(String firstName, String emailId) {
		this.firstName = firstName;
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
