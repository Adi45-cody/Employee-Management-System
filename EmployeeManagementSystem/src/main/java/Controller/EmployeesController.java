package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.EmployeeService;

import entity.Department;
import entity.DepartmentRepository;
import entity.EmployeeRepository;
import entity.Employees;

import jakarta.validation.Valid;

@Controller
public class EmployeesController {

    private final EmployeeRepository repo;
    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    public EmployeesController(EmployeeRepository repo, EmployeeService employeeService, DepartmentRepository departmentRepository) {
        this.repo = repo;
        this.employeeService = employeeService;
        this.departmentRepository=departmentRepository;
    }

    // Home page
    @GetMapping("/main")
    public String home() {
        return "index";
    }

    // Employee form
    @GetMapping("/employeeForm")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employees());
        return "employeeForm";
    }

    // Save Employee
    /*@PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employees employee,
                               BindingResult result, Model model) {

        // Validate user input first
        if (result.hasErrors()) {
            return "employeeForm";
        }

        /* Generate Employee ID after validation
        Department dept = employee.getDepartment();
        if (dept == null ) {
            
        }
        
        employee.setEmpId(employeeService.generateEmpId(dept));

        Department dept = employee.getDepartment();
        if (dept != null && dept.getName() != null && !dept.getName().isEmpty()) {
            // Try to find existing department by name
            dept = employeeService.findOrCreateDepartmentByName(dept.getName());
            employee.setDepartment(dept);
        }

        // Generate Employee ID
        employee.setEmpId(employeeService.generateEmpId(employee.getDepartment()));

        repo.save(employee);
        return "redirect:/main";
    }*/
    
    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employees employee,
                               BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employeeForm";
        }

        Department dept = employee.getDepartment();

        if (dept != null && dept.getName() != null && !dept.getName().trim().isEmpty()) {
            // Check if department already exists
            Department existingDept = departmentRepository.findByName(dept.getName())
                    .orElseGet(() -> {
                        // Save new department if not exists
                        Department newDept = new Department();
                        newDept.setName(dept.getName());
                        return departmentRepository.save(newDept);
                    });
            employee.setDepartment(existingDept);
        } else {
            // Default department
            Department unknownDept = departmentRepository.findByName("Unknown")
                    .orElseGet(() -> {
                        Department newDept = new Department();
                        newDept.setName("Unknown");
                        return departmentRepository.save(newDept);
                    });
            employee.setDepartment(unknownDept);
        }
        employee.setDepartmentName(employee.getDepartment().getName());
        // Generate Employee ID based on department name
        employee.setEmpId(employeeService.generateEmpId(employee.getDepartment()));

        repo.save(employee);
        return "redirect:/main";
    }






    // View all employees
    @GetMapping("/viewEmployees")
    public String viewEmployees(Model model) {
        model.addAttribute("employees", repo.findAll(org.springframework.data.domain.Sort.by("id").descending()));
        return "employees";
    }

    // View single employee
    @GetMapping("/viewEmp")
    public String viewEmp(@RequestParam("id") Long id, Model model) {
        Employees selectedEmp = repo.findById(id).orElse(null);
        model.addAttribute("selectedEmployee", selectedEmp);

        // This line to keep the table visible
        model.addAttribute("employees", repo.findAll(org.springframework.data.domain.Sort.by("id").descending()));

        return "employees"; // same Thymeleaf template
    }

    // Redirect root to main
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/main";
    }
}
