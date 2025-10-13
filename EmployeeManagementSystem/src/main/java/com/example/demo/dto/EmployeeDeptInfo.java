package com.example.demo.dto;

public class EmployeeDeptInfo {

	private String empName;
	private String departmentName;
	private String emailId;
	private String aadhaarNumber;
	private String panNumber;
	public EmployeeDeptInfo(String empName, String departmentName, String emailId, String aadhaarNumber,
			String panNumber) {
		super();
		this.empName = empName;
		this.departmentName = departmentName;
		this.emailId = emailId;
		this.aadhaarNumber = aadhaarNumber;
		this.panNumber = panNumber;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAadhaarNumber() {
		return aadhaarNumber;
	}
	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	
	
}//end of class
