package com.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.model.Department;
import com.department.service.DepartmentService;
import com.department.service.DepartmentServiceImpl;
import com.employee.dao.EmployeeRepositoryImpl;
import com.model.Employee;
import com.model.Project;
import com.project.service.ProjectService;
import com.project.service.ProjectServiceImpl;
import exception.DatabaseException;



/**
* <p>
* Employee Services class calls all the methods to add, delete, update and
* display the employee records in database.
* </p>
*/
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepositoryImpl employeeRepository;
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();

    public EmployeeServiceImpl() {
        employeeRepository = new EmployeeRepositoryImpl();
    }

    /**
    * <p>
    * Add all the parameter passed through the controller into the database
    * and makes the it ready at all for the end users. 
    * Here all the parameters are only passed through this layer.
    * </p>
    */
    public void addEmployee(int id, String name, LocalDate dob, String emailId,
                            String mobile, int deptId) 
                            throws IllegalArgumentException, DatabaseException {
        try {
            Department department = departmentService.getDepartmentById(deptId);
			System.out.println("name" + department.getName());
            Employee employee = new Employee(id, name, dob, department, emailId, mobile); 
            employeeRepository.addEmployee(employee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException ("No such department" + deptId);
        }     
    }

    
    /**
    * <p>
    * Delete the employee parameters by getting the employee id which
    * needs to be deleted and employee id should existing one. 
    * </p>
    */
    public void removeEmployee(int id) throws IllegalArgumentException, 
                                               DatabaseException {
        try {
            Employee employee = employeeRepository.findEmployeeById(id);
            if (employee != null) {
                employeeRepository.deleteEmployee(id);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee not found" +id);
        }
    }
    
    /**
    * <p>
    * returns all the employee from the database and hereafter it is 
    * filtered as per the user request.
    * </p>
    */
    public List<Employee> getAllEmployees() throws DatabaseException {
        return employeeRepository.getAllEmployees();
    }

    /**
    * <p>
    * Getting an employee using the employee Id to show their profile alone
    * from the database.
    * </p>
    */
    public Employee getEmployeeById(int id) throws DatabaseException {
        return employeeRepository.findEmployeeById(id);
    }        

    /**
    * <p>
    * Upadte all the parameter passed through the controller into the database
    * with all the validations same on add method. And before updating select
    * an existing department in database.
    * </p>
    */
    public void updateEmployee(int id, String name, LocalDate dob,
                               String emailId, String mobile, int deptId)
                               throws IllegalArgumentException, DatabaseException {
        try {
            Employee employee = employeeRepository.findEmployeeById(id);
            if (employee != null) {
                employee.setName(name);
                employee.setDob(dob);
                employee.setEmailId(emailId);

                Department department = departmentService.getDepartmentById(deptId);
                if (department != null) {
                    employee.setDepartment(department);
                } 
                employeeRepository.updateEmployee(employee);
            } 
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee not found" +id);
        }
    }

    /**
    * <p>
    * Getting an employee using the department Id to show their profile alone
    * from the database.
    * </p>
    */
    public Department getDepartmentById(int id) throws DatabaseException {
        return departmentService.getDepartmentById(id);
    }
    
    /**
    * <p>
    * Returns all the department list which is used to show the available
    * departments while assigning the department to an employee.
    * </p>
    */
    public List<Department>getAllDepartment() throws DatabaseException {
       return departmentService.getAllDepartments();
    }

    /**
    * <p>
    * Adds project to an employee to mention that employee is working in
    * which project.
    * </p>
    */

    public void addProjectToEmployee(int employeeId, int projectId) throws 
                          IllegalArgumentException, DatabaseException {

        try {
            Employee employee = getEmployeeById(employeeId);
            Project project = projectService.getProjectById(projectId);
            employeeRepository.addProjectToEmployee(employeeId, projectId);
        
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee or Project not found" + projectId + employeeId);
        }
    }


    /**
    * <p>
    * Removes project from employee by using the employee and project id
    * </p>
    */

    public void removeProjectFromEmployee(int employeeId, int projectId) throws 
                          IllegalArgumentException, DatabaseException {
        try {
            Employee employee = getEmployeeById(employeeId);
            Project project = projectService.getProjectById(projectId);
            employeeRepository.removeProjectFromEmployee(employeeId, projectId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee or Project not found" + projectId + employeeId);
        }
    }
    
    /**
    * <p>
    * Returns all the projects from database and it is shown as Current project
    * which is used to assign single or multiple projects to an employee.
    * </p>
    */
    public List<Project> getAllProjects() throws DatabaseException {
        return projectService.getAllProjects();
    }
}

