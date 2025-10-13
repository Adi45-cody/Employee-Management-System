package com.example.demo.dto;

import java.time.LocalDate;

import entity.Department;
import entity.Employees;

public class EmployeeDTO {

	private String empId;
    private String empName;
    private LocalDate dob;
    private String presentAddress;
    private String permanentAddress;
    private String gender;
    private String bloodGroup;
    private String maritalStatus;
    private String emailId;
    private String contact;
    private Department department;
    private String aadhaarNumber;
    private String panNumber;
    private Long departmentId;
    private String departmentName;
    
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
	
    
    
    public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public EmployeeDTO() {
		
	}
	private Double salary;
    private String qualification;
    private String university;
    private Integer yearOfPassing;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getPresentAddress() {
		return presentAddress;
	}
	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department2) {
		this.department = department2;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public Integer getYearOfPassing() {
		return yearOfPassing;
	}
	public void setYearOfPassing(Integer yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}
	
	
	// Convert DTO to Entity
    public Employees convertToEntity(Department dept) { 
        Employees emp = new Employees();
        emp.setEmpId(this.empId); 
        emp.setEmpName(this.empName);
        emp.setEmailId(this.emailId);
        emp.setGender(this.gender);
        emp.setYearOfPassing(this.yearOfPassing);
        emp.setBloodGroup(this.bloodGroup);
        emp.setMaritalStatus(this.maritalStatus);

        emp.setDepartment(dept); 
        return emp;
    }

    // Convert Entity to DTO
    public static EmployeeDTO fromEntity(Employees emp) { 
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpId(emp.getEmpId());
        dto.setEmpName(emp.getEmpName());
        dto.setEmailId(emp.getEmailId());
        dto.setDepartmentName(emp.getDepartmentName());
        dto.setGender(emp.getGender());
        dto.setYearOfPassing(emp.getYearOfPassing());
        dto.setBloodGroup(emp.getBloodGroup());
        dto.setMaritalStatus(emp.getMaritalStatus());

        if(emp.getDepartment() != null) { 
            dto.setDepartmentId(emp.getDepartment().getId()); 
            dto.setDepartmentName(emp.getDepartment().getName()); 
        }
        return dto;
    }
	
}//end of class
