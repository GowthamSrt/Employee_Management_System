package com.employee.controller;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.department.controller.DepartmentController;
import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;
import com.exception.DatabaseException;
import com.model.Department;
import com.model.Employee;
import com.model.Project;
import com.utils.EmployeeValidator;

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
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static int idCounter = 1;
	private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    private Scanner scanner = new Scanner(System.in);

    /**
     * To make the user choice and navigating through the selection. 
     */
    public void displayEmployeeManagement() {
		try {
			while (true) {
				System.out.println("-------------------------------------");
				System.out.println("     Employee Management System      ");
				System.out.println("-------------------------------------"); 
				System.out.println("1) Add Employee");
				System.out.println("2) Update Employee");
				System.out.println("3) Display All Employees");
				System.out.println("4) Display Employee by ID");
				System.out.println("5) Delete Employee");
				System.out.println("6) Add Project To Employee");
				System.out.println("7) Remove Project From Employee");
				System.out.println("8) Back to Main Menu");
				System.out.println("-------------------------------------"); 
				System.out.println("Enter your choice: ");
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
            } catch (InputMismatchException e) {
				logger.error("Please Enter a Valid Option (Numeric only)");
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
                System.out.println("Enter employee Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                System.out.println("InValid Name Entered");
                } else {
                    checkName = true;
                }
             }
            
            while(!checkDob){
                System.out.println("Enter employee DOB (dd-MM-yyyy): ");

				try {
					dob = LocalDate.parse(scanner.nextLine(),dateFormat);
					int age = Period.between(dob, LocalDate.now()).getYears();
                if (age < 18 || age > 65) {
                    throw new IllegalArgumentException("Age should be"
	                                  +" between 18 and 65.");
                } 
                else {
                    checkDob = true;
                }
				} catch (DateTimeParseException e) {
					System.out.println("Invalid Date Format. Date format is dd-MM-yyyy");
				}
            }
			
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                email = scanner.nextLine();
                if(!employeeValidator.isValidEmail(email)) {
                    System.out.println("InValid email format");
                } else {
                    checkEmail = true;
                }
            }

            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.nextLine();
            if (!mobile.matches("^\\d{10}$")) {
				System.out.println("Invalid Mobile number format");
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
            logger.info("Employee added successfully.");
            idCounter++;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (DatabaseException e) {
            logger.error("Error while adding" + e);
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
                System.out.println("Employee ID Not Found" + id);
                return;
            }
                   
            scanner.nextLine();

            while(!checkName){
                System.out.print("Enter employee Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                    System.out.println("InValid Name Entered");
                } else {
                    checkName = true;
                }
             }
            
            while(!checkDob){
                System.out.print("Enter employee DOB (dd-MM-yyyy): ");

				try {
					dob = LocalDate.parse(scanner.nextLine(),dateFormat);
					int age = Period.between(dob, LocalDate.now()).getYears();
                if (age < 18 || age > 65) {
                    throw new IllegalArgumentException("Age should be"
	                                  +" between 18 and 65.");
                } 
                else {
                    checkDob = true;
                }
				} catch (DateTimeParseException e) {
					System.out.println("Date format is dd-MM-yyyy");
				}
            }
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                email = scanner.nextLine();
                if(!employeeValidator.isValidEmail(email)) {
                    System.out.println("InValid email format");
                } else {
                    checkEmail = true;
                }
            }

            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.nextLine();
            if (!mobile.matches("^\\d{10}$")) {
				System.out.println("Invalid Mobile number format");
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
            logger.info("Employee updated successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (DatabaseException e) {
             logger.error("Error while update" + e);
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
            logger.info("Employee deleted successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (DatabaseException e) {
            logger.error("Error while removing" + e);
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
            logger.error("No Employees to display" + e);
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
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (DatabaseException e) {
            logger.error("No employee found" + e);
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
            logger.info("Project added to employee successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (DatabaseException e) {
            logger.error("No such project" + e);
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
            logger.info("Project removed from employee successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (InputMismatchException e) {
		    logger.error("Please Enter a Valid Option (Numeric only)");
		} catch (DatabaseException e) {
            logger.error("No such project" + e);
        }
    }
}
