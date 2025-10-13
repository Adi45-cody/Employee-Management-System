package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeDeptInfo;
import com.example.demo.dto.EmployeeSummDTO;

import entity.Department;
import entity.DepartmentRepository;
import entity.EmployeeRepository;
import entity.Employees;

@Service //Creates an object of EmployeeService spring finds @service and creates a bean of EmployeeService
public class EmployeeService {

    @Autowired  //@Autowired - tells Spring to inject that bean wherever needed  spring finds autowired automatically injects that bean into EmployeeRestController
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public List<Employees> getEmployeesByDepartmentId(Long departmentId) {
        // Fetch the Department entity first
        Department dept = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));

        // Fetch employees by Department entity
        return employeeRepository.findByDepartment(dept);
    }

    public String generateEmpId(Department department) {
        String deptName = "EMP";
        if (department != null && department.getName() != null && !department.getName().isEmpty()) {
            deptName = department.getName();
        }

        String prefix = deptName.length() >= 3 ? deptName.substring(0, 3).toUpperCase() : deptName.toUpperCase();
        long count = employeeRepository.count() + 1;
        return prefix + String.format("%03d", count);
    }


    
 // Save
    public Employees save(Employees employee) {
        return employeeRepository.save(employee);
    }

    // Find all
    public List<Employees> findAll() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // Find by ID
    public Employees findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // Update
    public Employees update(Long id, Employees data) {
        Employees existing = findById(id);

        existing.setEmpName(data.getEmpName());
        existing.setDob(data.getDob());
        existing.setPresentAddress(data.getPresentAddress());
        existing.setPermanentAddress(data.getPermanentAddress());
        existing.setGender(data.getGender());
        existing.setBloodGroup(data.getBloodGroup());
        existing.setMaritalStatus(data.getMaritalStatus());
        existing.setEmailId(data.getEmailId());
        existing.setContact(data.getContact());
        existing.setDepartment(data.getDepartment());
        existing.setSalary(data.getSalary());
        existing.setQualification(data.getQualification());
        existing.setUniversity(data.getUniversity());
        existing.setYearOfPassing(data.getYearOfPassing());
        existing.setAadhaarNumber(data.getAadhaarNumber());
        existing.setPanNumber(data.getPanNumber());

        return employeeRepository.save(existing);
    }

    // Delete
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }	
    
    public EmployeeDTO convertToDTO(Employees emp) {
    	
    	EmployeeDTO dto = new EmployeeDTO();
    	dto.setEmpId(emp.getEmpId());
        dto.setEmpName(emp.getEmpName());
        dto.setDob(emp.getDob());
        dto.setPresentAddress(emp.getPresentAddress());
        dto.setPermanentAddress(emp.getPermanentAddress());
        dto.setGender(emp.getGender());
        dto.setBloodGroup(emp.getBloodGroup());
        dto.setMaritalStatus(emp.getMaritalStatus());
        dto.setEmailId(emp.getEmailId());
        dto.setContact(emp.getContact());
        dto.setDepartment(emp.getDepartment());
        dto.setSalary(emp.getSalary());
        dto.setQualification(emp.getQualification());
        dto.setUniversity(emp.getUniversity());
        dto.setYearOfPassing(emp.getYearOfPassing());
        
        if (emp.getDepartment() != null) {
            dto.setDepartmentId(emp.getDepartment().getId());
            dto.setDepartmentName(emp.getDepartment().getName());
        }
        
        return dto;
    }
    
    public Employees covertToEntity(EmployeeDTO dto) {
    	
    	Employees emp = new Employees();
    	emp.setEmpId(dto.getEmpId());
        emp.setEmpName(dto.getEmpName());
        emp.setDob(dto.getDob());
        emp.setPresentAddress(dto.getPresentAddress());
        emp.setPermanentAddress(dto.getPermanentAddress());
        emp.setGender(dto.getGender());
        emp.setBloodGroup(dto.getBloodGroup());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setEmailId(dto.getEmailId());
        emp.setContact(dto.getContact());
        emp.setDepartment(dto.getDepartment());
        emp.setSalary(dto.getSalary());
        emp.setQualification(dto.getQualification());
        emp.setUniversity(dto.getUniversity());
        emp.setYearOfPassing(dto.getYearOfPassing());
        
        if (dto.getDepartmentId() != null) {
            Department dept = getDepartmentById(dto.getDepartmentId());
            emp.setDepartment(dept);
        }
        
        Department dept = null;

        if (dto.getDepartmentId() != null) {
            dept = getDepartmentById(dto.getDepartmentId());
        } else if (dto.getDepartmentName() != null && !dto.getDepartmentName().isEmpty()) {
            dept = departmentRepository.findByName(dto.getDepartmentName())
                    .orElseGet(() -> {
                        Department newDept = new Department();
                        newDept.setName(dto.getDepartmentName());
                        return departmentRepository.save(newDept);
                    });
        }

        emp.setDepartment(dept);
        emp.setDepartmentName(dept.getName());
        return emp;
    }
    
    public List<EmployeeSummDTO> getEmployeeSummaries() {
        List<Employees> employees = employeeRepository.findAll();
        List<EmployeeSummDTO> dtos = new ArrayList<>();

        for (Employees e : employees) {
            dtos.add(new EmployeeSummDTO(
                e.getId(),
                e.getEmpId(),
                e.getEmpName(),
                e.getDepartment()
            ));
        }

        return dtos;
    }

    public List<Employees> getEmployeesByDepartment(Department dept){
    	return employeeRepository.findByDepartment(dept);
    }
   
    public Department findOrCreateDepartmentByName(String name) {
        return departmentRepository.findByName(name)
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setName(name);
                    return departmentRepository.save(newDept);
                });
    }

    public List<Employees> getEmployeesByGender(String gender){
		return employeeRepository.findByGender(gender);
    	
    }
    
    public List<EmployeeDeptInfo> getEmployeesByDept(Long deptId) {
        return employeeRepository.findEmployeesByDepartmentId(deptId);
    }
    
    public List<Employees> getEmployeesByMaritalStatus(String maritalStatus){
    	return employeeRepository.findByMaritalStatus(maritalStatus);
    }
    
    public List<EmployeeDeptInfo> getEmployeesByDepartmentName(String departmentName){
    	return employeeRepository.findByDepartmentName(departmentName);
    }
}