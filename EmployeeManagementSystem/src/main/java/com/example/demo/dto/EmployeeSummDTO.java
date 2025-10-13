package com.example.demo.dto;

import entity.Department;

public class EmployeeSummDTO {

	private Long id;
    private String empId;
    private String empName;
    private Department department;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getName() {
		return empName;
	}
	public void setName(String name) {
		this.empName = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public EmployeeSummDTO(Long id, String empId, String name, Department department2) {
		super();
		this.id = id;
		this.empId = empId;
		this.empName = name;
		this.department = department2;
	}
	
}//end of class
