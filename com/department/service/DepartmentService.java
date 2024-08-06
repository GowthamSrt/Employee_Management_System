package com.department.service;

import java.util.List;

import com.department.dao.DepartmentRepositoryImpl;
import com.department.service.DepartmentServiceImpl;
import com.model.Department;
import com.model.Employee;
import com.employee.service.EmployeeService;
import com.exception.DatabaseException;

/**
 *<p>
 * Interface for employeeservice  to handle  department-related operation.
 *</p>
 *
 */
public interface DepartmentService {

    /**
     * Adds a new department to the repository.
     * @param id - The unique identifier for the department.
     * @param name - The name of the department.
     */
    public void addDepartment(int id, String name) throws DatabaseException;

    /**
     * Remove a department from the repository by its Id.
     *
     * @param id - The unique identifier of the department to be removed.
     * @thows IllegalArgumentException if the department with the given ID is not found.
     */
    public void removeDepartment(int id) throws IllegalArgumentException, DatabaseException;

    /**
     * Retrieves all department from the repository.
     * @return A List of all department.
     */ 
    public List<Department> getAllDepartments() throws DatabaseException;

    /**
     * Retrieves a department by its unique identifier.
     *
     *@param id - The unique identifier of the department.
     *@return The department with the given ID, or null if not found.
     */
    public Department getDepartmentById(int id) throws IllegalArgumentException, DatabaseException;

    /**
     * Updates the details of an existing department.
     * @param id - the unique identifier of the departmnet to the updated.
     * @param name - The new name for the department.
     * @thows IllegalArgumentException if the department with the given ID is not found.
     */
    public void updateDepartment(int id, String name) throws IllegalArgumentException, DatabaseException;
    
	/**
     * Retrieves all employees from a specific departemnt.
     * @return A List of all employees in a department.
     */ 
    public List<Employee> getEmployeesByDepartmentId(int id) throws DatabaseException;

}
