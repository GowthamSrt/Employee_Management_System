package com.department.service;

import java.util.List;

import com.department.dao.DepartmentRepositoryImpl;
import com.model.Department;
import com.model.Employee;
import com.employee.service.EmployeeService;
import com.department.service.DepartmentService;
import exception.DatabaseException;

/**
* <p>
* Class implements the services related with department management and there
* should be no null values provided.
* </p>
*/
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

    /**
    * <p>
    * Method adds the department into the database which must have Id and Name
    * and it should not be left blank or empty.
    * </p>
    * @param id denotes the id of department
    * @param name denotes the name.
    */
    public void addDepartment(int id, String name) throws DatabaseException {
        Department department = new Department(id, name);
        departmentRepository.addDepartment(department);
    }

    /**
    * <p>
    * To delete the department, only if there is no employees in that 
    * particular department else it'll not accept.
    * </p>
    */
    public void removeDepartment(int id) throws IllegalArgumentException, DatabaseException {
        Department department = departmentRepository.findDepartmentById(id);
        try {
            departmentRepository.deleteDepartment(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Department not found : " +id);
        }
    }

    /**
    * <p>
    * To get all the departments to display while selecting the particular
    * deoartment to a employee.
    * </p>
    */ 
    public List<Department> getAllDepartments() throws DatabaseException {
        return departmentRepository.getAllDepartments();
    }

    /**
    * <p>
    * To get the department by Id to make the operations needed.
    * </p>
    */
    public Department getDepartmentById(int id) throws DatabaseException {
        return departmentRepository.findDepartmentById(id);
    }

    /**
    * <p>
    * Method will update the existing and be sure to update the existing
    * department.
    * </p>
    */
    public void updateDepartment(int id, String name) throws IllegalArgumentException, DatabaseException {
        Department department = departmentRepository.findDepartmentById(id);
        try {
            department.setName(name);
            departmentRepository.updateDepartment(department);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Department not found"+id);
        }
    }

    /**
     * Finds employees by department ID.
     * @param Id - deparmnetID of the department.
     * @return list employee.
     */
    public List<Employee> getEmployeesByDepartmentId(int id) throws DatabaseException {
        return departmentRepository.getEmployeesByDepartmentId(id);
    }

}
