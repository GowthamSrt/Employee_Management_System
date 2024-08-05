package com.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.Period;

import com.department.controller.DepartmentController;
import com.model.Department;
import com.model.Employee;
import com.model.Project;
import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;
import exception.DatabaseException;
import utils.EmployeeValidator;

/**
* <h1>Employee Controller</h1>
* This program implements an application that used to manage the employee
* records with some validations & logics and returns the outputs
* as per the request. 
* <p>
* @author  Gowthamraj
*/
public class EmployeeController {
    private EmployeeService employeeService = new EmployeeServiceImpl();
    private EmployeeValidator employeeValidator = new EmployeeValidator();
    private static int idCounter = 1; 
    private Scanner scanner = new Scanner(System.in);

    /**
     * To make the user choice and navigating through the selection. And
     * 
     */
    public void displayEmployeeManagement() {
        while (true) {
            System.out.println("Employee Management System");
            System.out.println("---------------------------------"); 
            System.out.println("1) Add Employee");
            System.out.println("2) Update Employee");
            System.out.println("3) Display All Employees");
            System.out.println("4) Display Employee by ID");
            System.out.println("5) Delete Employee");
            System.out.println("6) Add Project To Employee");
            System.out.println("7) Remove Project From Employee");
            System.out.println("8) Back to Main Menu");
            System.out.println("---------------------------------"); 
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    updateEmployee();
                    break;
                case 3:
                    displayAllEmployees();
                    break;
                case 4:
                    displayEmployeeById();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                    
                 case 6:
                    addProjectToEmployee();
                    break;
                case 7:
                    removeProjectFromEmployee();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Prompts the user to create a new employess.
     * @param employeeId an Id to specify an employee & unique for everyone
     * @param firstName First Name of employee
     * @param lastName Last Name of the employee
     * @param dob Date of Birth of the employee
     * @param email Mail id of employee
     * @param mobile Contact number of employee
     * @param department employee belongs to which department, Before adding employee
     * please add atleast one department to add employees.
     * It can be even empty & later it can be updated.
     * </p>
     */
    public void addEmployee() {
        try {
            boolean checkName = false;
            boolean checkDob = false;
            boolean checkEmail = false;
            LocalDate dob = null;
            String name = "";
            String email = "";

            while(!checkName){
                System.out.print("Enter employee Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                System.out.println("InValid");
                } else {
                    checkName = true;
                }
             }
            
            while(!checkDob){
                System.out.print("Enter employee DOB (YYYY-MM-DD): ");
                dob = LocalDate.parse(scanner.nextLine());

                int age = Period.between(dob, LocalDate.now()).getYears();
                if (age < 18 || age > 65) {
                    throw new IllegalArgumentException("Age should be"
	                                  +" between 18 and 65.");
                } 
                else {
                    checkDob = true;
                }
            }
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                email = scanner.nextLine();
                if(!employeeValidator.isValidEmail(email)) {
                    System.out.println("InValid");
                } else {
                    checkEmail = true;
                }
            }

            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.nextLine();
            if (!mobile.matches("^\\d{10}$")) {
                return;
            }
            scanner.nextLine();
 
            for(Department departments : employeeService.getAllDepartment()){
               System.out.println("Department ID= " + departments.getId() 
                                  + "   " + "Department Name = " 
                                  + "   " +departments.getName());
            }

            System.out.print("Enter department Id: ");
            int deptId = scanner.nextInt();
            scanner.nextLine();
            

            employeeService.addEmployee(idCounter, name, dob, email, mobile, deptId);
            System.out.println("Employee added successfully.");
            idCounter++;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Error while adding" + e);
        }
        
    }


     /**
    * <p>
    * Updation method which updates the record. All the add employee
    * criteria are applicable here to update the details.
    * </p>
    */

    public void updateEmployee() {
        try {
            boolean checkName = false;
            boolean checkDob = false;
            boolean checkEmail = false;
            LocalDate dob = null;
            String name = "";
            String email = "";
            System.out.print("Enter employee Id: ");
            int id = scanner.nextInt();
            if(employeeService.getEmployeeById(id) == null) {
                System.out.println("Employee ID Not Found" +id);
                return;
            }
                   
            scanner.nextLine();

            while(!checkName){
                System.out.print("Enter employee Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                System.out.println("InValid");
                } else {
                    checkName = true;
                }
             }
            
            while(!checkDob){
                System.out.print("Enter employee DOB (YYYY-MM-DD): ");
                dob = LocalDate.parse(scanner.nextLine());
                if(!employeeValidator.isValidFutureDate(dob)){
                    System.out.println("Invalid: Date in Future");
                } else {
                    checkDob = true;
                }
            }
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                email = scanner.nextLine();
                if(!employeeValidator.isValidEmail(email)) {
                    System.out.println("InValid");
                } else {
                    checkEmail = true;
                }
            }

            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.nextLine();
            if (!mobile.matches("^\\d{10}$")) {
                return;
            }
            
             for(Department departments : employeeService.getAllDepartment()){
               System.out.println("ID = " + departments.getId() + " name = " 
                                   + departments.getName());
            }

            System.out.print("Enter department Id: ");
            int deptId = scanner.nextInt();
            scanner.nextLine();

            employeeService.updateEmployee(id, name, dob, email, mobile, deptId);
            System.out.println("Employee updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
             System.out.println("Error while update" + e);
        }
    }

    /**
    * Making soft delete by Id to hide those employee details from the request
    * by using a boolean value isActive.
    */
    public void deleteEmployee() {
        try {
            System.out.print("Enter Employee Id to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            employeeService.removeEmployee(id);
            System.out.println("Employee deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Error while removing" + e);
        }
    }
    
    /**
    *<p>
    * Displaying all the employee details based on a specified format
    * by using String format
    * </p>
    */
    public void displayAllEmployees() {
        try {
            System.out.printf("%-10s  %-20s  %-15s  %-20s  %-25s %-15s\n ","ID",
                            "Name","Age",  "Department Name", "Email", "Mobile No");
            System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
            List<Employee> employees = employeeService.getAllEmployees();
            for (Employee employee : employees) {
                System.out.println(employee);
            }
            System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
        } catch (DatabaseException e) {
            System.out.println("No Employees to display" + e);
        }
    }
    
    /**
    * <p>
    * Displaying the employee by using employee Id to show all of their 
    * details from the database.
    *</p>
    */
    public void displayEmployeeById() {
        try {
            System.out.print("Enter Employee Id to display: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                System.out.printf("%-10s  %-20s  %-15s  %-20s  %-25s %-15s\n ",
                                  "ID", "Name","Age",  "Department Name",
                                  "Email", "Mobile No");
                System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
                System.out.println(employee);
                System.out.println("-----------------------------------------"
                                   + "---------------------------------------"
                                   + "-------------------------");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("No employee found" + e);
        }
    }

   
    
    /**
    * <p>
    * Add project to employee by fetching the project Id with employee Id.
    * </p>
    */
    public void addProjectToEmployee() {
        try {
            System.out.println("Enter Employee Id: ");
            int employeeId = scanner.nextInt();

            for (Project project : employeeService.getAllProjects()) {
            System.out.println("ID = " +project.getId()+ " Name = " +project.getName());
        }
            System.out.print("Enter Project Id: ");
            int projectId = scanner.nextInt();
            scanner.nextLine();

            employeeService.addProjectToEmployee(employeeId, projectId);
            System.out.println("Project added to employee successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("No such project" + e);
        }
    }
    
    /**
    * <p>
    * Removes project from the employee who associated with that using both
    * employee and project id.
    *</p>
    */
    public void removeProjectFromEmployee() {
        try {
            System.out.print("Enter Employee Id: ");
            int employeeId = scanner.nextInt();
            System.out.print("Enter Project Id: ");
            int projectId = scanner.nextInt();
            scanner.nextLine();

            employeeService.removeProjectFromEmployee(employeeId, projectId);
            System.out.println("Project removed from employee successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("No such project" + e);
        }
    }
}
