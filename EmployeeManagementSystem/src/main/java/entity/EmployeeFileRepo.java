package entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeFileRepo extends JpaRepository<EmployeeFile, Long> {

	// Get all files for a specific employee
    List<EmployeeFile> findByEmployeeId(String employeeId);
	
    //Get files for a specific employee and type
    List<EmployeeFile> findByEmployeeIdAndFiletype(String employeeId, com.example.demo.model.FileType filetype);
}
