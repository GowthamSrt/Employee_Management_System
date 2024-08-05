package com.department.dao;

import com.model.Department;
import com.model.Employee;
import exception.DatabaseException;

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
import utils.DatabaseConnection;

public interface DepartmentRepository {
   /**
    * <p>
    * method which adds the departmentId and name to the database with 
    * checking whether departmentId alreday available or not.
    * </p>
    * @param departmentId
    * @param departmentName
    */
    public void addDepartment(Department department) throws DatabaseException;

    /**
    * <p>
    * hanldes the updation of department by checking whether department already
    * added and then it permits for updation.
    * </p>
    */
    public void updateDepartment(Department department) throws DatabaseException;

    /**
    * <p>
    * method deletes the department from the database by getting Id from user
    * </p>
    */
    public void deleteDepartment(int id) throws DatabaseException;

    /**
    * <p>
    * display all the departments in a table of Id and name of departments.
    * </p>
    */
    public List<Department> getAllDepartments() throws DatabaseException;

    /**
    * <p>
    * method to filter out the department using the department ID to view the
    * employees in a specific department.
    * </p>
    */
    public Department findDepartmentById(int id) throws DatabaseException;

    /**
    * <p>
    * Get all the departments that are currently available in the database.
    * </p>
    */
    public List<Employee> getEmployeesByDepartmentId(int departmentId) throws DatabaseException;

}