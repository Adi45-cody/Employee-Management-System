package Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.EmployeeDeptInfo;
import com.example.demo.dto.EmployeeSummDTO;
import com.example.demo.service.EmployeeService;

import entity.Employees;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*") // allow calls from Postman/front ends during testing
@Tag(name = "Employee APIs", description = "APIs to manage employees")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(summary="Create Employee", description="Creates a new employee record")
    public ResponseEntity<EmployeeDTO> createEmployee(@Parameter(description = "Employee data in JSON format", required = true)@Valid @RequestBody EmployeeDTO dto) {
        // convert DTO to entity (this will handle department creation if needed)
        Employees emp = employeeService.covertToEntity(dto);
        
        // generate empId before saving
        emp.setEmpId(employeeService.generateEmpId(emp.getDepartment()));
        
        // save employee
        Employees saved = employeeService.save(emp);
        
        // return DTO with department info
        return ResponseEntity.ok(employeeService.convertToDTO(saved));
    }


    @GetMapping
    @Operation(summary = "Get All Employees", description = "Returns a list of all employees")
    public ResponseEntity<List<EmployeeSummDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getEmployeeSummaries());
    }

    

    @GetMapping("/{id}")
    @Operation(summary = "Get Employee by ID", description = "Returns a single employee by ID")
    public ResponseEntity<EmployeeDTO> getById(@Parameter(description = "ID of the employee", required = true)@PathVariable Long id) {
    	Employees emp = employeeService.findById(id);
        return ResponseEntity.ok(employeeService.convertToDTO(emp));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Employee", description = "Updates an existing employee by ID")
    public ResponseEntity<EmployeeDTO> update(@Parameter(description = "ID of the employee to update", required = true)@PathVariable Long id, @Parameter(description = "Updated employee data", required = true) @Valid @RequestBody EmployeeDTO dto) {
        Employees emp = employeeService.covertToEntity(dto);
        Employees updated = employeeService.update(id, emp);
        return ResponseEntity.ok(employeeService.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee", description = "Deletes an employee by ID")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the employee to delete", required = true)@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
   /* @GetMapping("/bydepartment")
    public List<Employees> getByDepartment(@RequestParam Department dept){
    	return employeeService.getEmployeesByDepartment(dept);
    }
    /*
    @GetMapping("/bydepartment")
    public ResponseEntity<List<Employees>> getByDepartment(@RequestParam Long departmentId) {
        // Fetch Department entity from DB
        Department dept = employeeService.getDepartmentById(departmentId);
        List<Employees> employees = employeeService.getEmployeesByDepartment(dept);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/bydepartment")
    public List<Employees> getByDepartment(@RequestParam Long departmentId) {
        return employeeService.getEmployeesByDepartmentId(departmentId);
    }*/

    @GetMapping("/bydepartment")
    @Operation(summary = "Get Employees by Department ID", description = "Fetches employees of a specific department by ID")
    public ResponseEntity<List<EmployeeDTO>> getByDepartment(@Parameter(description = "ID of the department", required = true)@RequestParam Long departmentId) {
        List<Employees> employees = employeeService.getEmployeesByDepartmentId(departmentId);
        
        // Convert each employee to DTO
        List<EmployeeDTO> dtos = employees.stream()
                                          .map(employeeService::convertToDTO)
                                          .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/gender/{gender}")
    @Operation(summary = "Get Employees by Gender", description = "Fetches employees filtered by gender")
    public ResponseEntity<List<Employees>> getEmployeesByGender(@Parameter(description = "Gender to filter (Male/Female)", required = true)@PathVariable String gender) {
        List<Employees> employees = employeeService.getEmployeesByGender(gender);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/bydepartmentinfo")
    @Operation(summary = "Get Department Info", description = "Fetches selected employee details for a department")
    public List<EmployeeDeptInfo> getDeptInfo( @Parameter(description = "ID of the department", required = true)@RequestParam Long departmentId) {
        return employeeService.getEmployeesByDept(departmentId);
    }
    
    @GetMapping("/maritalStatus/{maritalStatus}")
    @Operation(summary = "Get Employees by Marital Status", description = "Fetches employees filtered by marital status")
    public List<Employees>getEmployeesByMaritalStatus(@Parameter(description = "Marital status to filter (Single/Married)", required = true)@PathVariable String maritalStatus){
    	return employeeService.getEmployeesByMaritalStatus(maritalStatus);
    }
    
    @GetMapping("/bydepartmentname")
    @Operation(summary = "Get Employees by Department Name", description = "Fetches employee info by department name")
    public List<EmployeeDeptInfo> getByDepartmentName(@Parameter(description = "Name of the department", required = true)@RequestParam String departmentName) {
        return employeeService.getEmployeesByDepartmentName(departmentName);
    }

}