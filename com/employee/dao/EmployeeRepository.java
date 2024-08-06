package com.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.model.Employee;
import com.employee.service.EmployeeService;
import com.exception.DatabaseException;

public interface EmployeeRepository {
    /**
    * <p>
    * Method which adds the details of employee taken from controller.
    * </p>
    */
    public void addEmployee(Employee employee) throws DatabaseException;

    /**
    * <p>
    * Method which delete the details of employee by using the employee Id.
    * </p>
    */
    public void deleteEmployee(int id) throws DatabaseException;

    /**
    * <p>
    * Returns all the employees from the database.
    * </p>
    */
    public List<Employee> getAllEmployees() throws DatabaseException;

    /**
    * <p>
    * Method return a single employee by using the employee Id search.
    * </p>
    */
    public Employee findEmployeeById(int id) throws DatabaseException;

    /**
    * <p>
    * Method which updates the details of employee taken from controller by
    * Checking already exist or not.
    * </p>
    */
    public void updateEmployee(Employee employee) throws DatabaseException;

    /**
    * <p>
    * Adds a project to an employee by fetching the project and employee
    * id.
    * </p>
    */    
    public void addProjectToEmployee(int employeeId, int projectId) throws DatabaseException;

    /**
     * <p>
     * Remove a project to an employee by fetching the project and employee
     * id.
     * </p>
     */ 
    public void removeProjectFromEmployee(int employeeId, int projectId) throws DatabaseException;

}