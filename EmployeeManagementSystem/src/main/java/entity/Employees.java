package entity;

import java.time.LocalDate;
//import java.time.LocalDateTime;

import com.example.demo.validation.Adult;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import jakarta.validation.constraints.Pattern;

@Entity //table in database
@JsonIgnoreProperties({"department"})
public class Employees {
@Id //primary key 
@GeneratedValue(strategy = GenerationType.IDENTITY) //automatically generate unique id for each employee 
	private long Id;
	
	//@Column(name = "created_at", updatable = false)
	//private LocalDateTime createdAt;
//employee details
	
	@Pattern(regexp = "^[A-Za-z0-9]+$", message="Employee Id must contain only letters and numbers")
	private String empId;
	
	@NotBlank(message = "Employee Name is required")	
	private String empName;
	
	@Past(message = "Employee must be 18+")
	@Adult
	private LocalDate dob;
	
	@NotBlank(message = "Present Address is required")
	private String presentAddress;
	
	@NotBlank(message = "PermanentAddress is required")
	private String permanentAddress;
	
	private String gender;
	private String bloodGroup;
	private String maritalStatus;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invaild email format")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",message = "Enter a valid email")
	private String emailId;
	
	@NotBlank(message = "Contact number is Required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact must be 10 digits")
	private String contact;
	
	//Job Details
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="department_id")
	@JsonManagedReference
	private Department department;
	
	@Column(name="department")
	private String departmentName;
	private Double salary;
	
	 // Education Details
    private String qualification;
    private String university;
    
    @Column(name="year_of_passing")
    private Integer yearOfPassing;
	
	// KYC
    
    @Pattern(regexp = "^[0-9]{12}$", message = "AadhaarNumber must be 12 digits")
    private String aadhaarNumber;
    
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Pan must follow format (ABCDE1234F)")
    private String panNumber;
    
    
    
	public Employees() {
		super();
	}
	public Employees(long id, String empId, String empName, LocalDate dob, String presentAddress, String permanentAddress,
			String bloodGroup, String maritalStatus, String emailId, String contact, Department department, Double salary,
			String qualification, String university, Integer yearOfPassing, String aadhaarNumber, String panNumber) {
		super();
		Id = id;
		this.empId = empId;
		this.empName = empName;
		this.dob = dob;
		this.presentAddress = presentAddress;
		this.permanentAddress = permanentAddress;
		this.bloodGroup = bloodGroup;
		this.maritalStatus = maritalStatus;
		this.emailId = emailId;
		this.contact = contact;
		this.department = department;
		this.salary = salary;
		this.qualification = qualification;
		this.university = university;
		this.yearOfPassing = yearOfPassing;
		this.aadhaarNumber = aadhaarNumber;
		this.panNumber = panNumber;
		
	}
	
	//@PrePersist
	//public void prePersist() {
		//this.createdAt = LocalDateTime.now();
	//}
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
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
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Double getSalary() {
		return salary;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
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
	public void setYearOfPassing( Integer yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
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
	@Override
	public String toString() {
		return "Employees [Id=" + Id + ", empId=" + empId + ", empName=" + empName + ", dob=" + dob
				+ ", presentAddress=" + presentAddress + ", permanentAddress=" + permanentAddress + ", bloodGroup="
				+ bloodGroup + ", maritalStatus=" + maritalStatus + ", emailId=" + emailId + ", contact=" + contact
				+ ", department=" + department + ", salary=" + salary + ", qualification=" + qualification
				+ ", university=" + university + ", yearOfPassing=" + yearOfPassing + ", aadhaarNumber=" + aadhaarNumber
				+ ", panNumber=" + panNumber + "]";
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}//end of class
