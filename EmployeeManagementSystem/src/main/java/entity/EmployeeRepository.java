package entity;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.EmployeeDeptInfo;

public interface EmployeeRepository extends JpaRepository<Employees, Long> {
//JpaRepository for save,delete,find,save methods 
	
	@Query("FROM Employees e Where e.department = :dept")
	List<Employees> findByDepartment(@Param ("dept") Department dept);
	
	@Query("FROM Employees e Where e.gender = :gender")
	List<Employees> findByGender(@Param("gender") String gender);
	
	@Query("SELECT new dto.EmployeeDeptInfo(e.empName, e.department.name,e.emailId,e.aadhaarNumber,e.panNumber)"+"FROM Employees e "+"WHERE e.department.id = :deptId")
	List<EmployeeDeptInfo> findEmployeesByDepartmentId(@Param("deptId") Long deptId);
	
	
	@Query("FROM Employees e Where e.maritalStatus = :maritalStatus")
	List<Employees>findByMaritalStatus(@Param ("maritalStatus") String maritalStatus);
	
	@Query("SELECT new dto.EmployeeDeptInfo(e.empName, e.department.name, e.emailId, e.aadhaarNumber, e.panNumber) " +"FROM Employees e " +"WHERE e.department.name = :deptName")
	    List<EmployeeDeptInfo> findByDepartmentName(@Param("deptName") String deptName);
	
}
