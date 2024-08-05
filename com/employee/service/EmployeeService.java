package com.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.model.Department;
import com.model.Employee;
import com.model.Project;
import exception.DatabaseException;

/**
 *<p>
 * Interface for employeeservice  to handle  employee-related operation.
 *</p>
 *
 */
public interface EmployeeService {

    
    void addEmployee(int id, String name, LocalDate dob, String emailId,
                            String mobile, int deptId) throws IllegalArgumentException, DatabaseException;

    
    void removeEmployee(int id) throws IllegalArgumentException, DatabaseException;

   
    List<Employee> getAllEmployees() throws DatabaseException;

    
    Employee getEmployeeById(int id) throws DatabaseException;

    
    void updateEmployee(int id, String name, LocalDate dob,
                                String emailId, String mobile, int deptId) throws IllegalArgumentException, DatabaseException;


    
    Department getDepartmentById(int id) throws DatabaseException;

    
    List<Department>getAllDepartment() throws DatabaseException;

    
    void addProjectToEmployee(int employeeId, int projectId) throws IllegalArgumentException, DatabaseException;

    
    void removeProjectFromEmployee(int employeeId, int projectId) throws IllegalArgumentException, DatabaseException;
    
    
    public List<Project> getAllProjects() throws DatabaseException;
}
