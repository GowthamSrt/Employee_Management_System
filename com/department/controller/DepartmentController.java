package com.department.controller;

import java.util.List;
import java.util.Scanner;
import utils.EmployeeValidator;

import com.department.service.DepartmentService;
import com.department.service.DepartmentServiceImpl;
import com.model.Employee;
import exception.DatabaseException;

/**
/**
* <h1>Department Controller</h1>
* <p>
* Handling all the department related inputs from user and outputs to
* display, department must be added before adding employee.
* </p>
*/
public class DepartmentController {
    private DepartmentService departmentService  = new DepartmentServiceImpl();
    private EmployeeValidator employeeValidator;
    private static int idCounter = 1; 
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the Department management menu and handles user choices.
     */
    public void displayDepartmentManagement() {
        while (true) {
            System.out.println("Department Management");
            System.out.println("-------------------------------");
            System.out.println("1) Add Department");
            System.out.println("2) Delete Department");
            System.out.println("3) Display All Departments");
            System.out.println("4) Update Department");
            System.out.println("5) Department Based Employee"); 
            System.out.println("6) Back to Main Menu");
            System.out.println("-------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createDepartment();
                    break;
                case 2:
                    deleteDepartment();
                    break;
                case 3:
                    displayAllDepartments();
                    break;
                case 4:
                    updateDepartment();
                    break;
                case 5:
                    displayEmployeesInDepartment();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
    *<p>
    * Method which allows to add departments along with department Id.
    * It checks whether Id is in database, if not in database then it adds
    *</p>
    *@param departmentId specifies a department
    *@param departmentName Name of the department
    */
    public void createDepartment() {
        try {
            String name = "";
            boolean checkName = false;
            scanner.nextLine();
            while (!checkName) {
                System.out.print("Enter Department Name: ");
                name = scanner.nextLine();
                if(!employeeValidator.isValidName(name)) {
                    System.out.println("InValid");
                } else {
                    checkName = true;
                }
            }

            departmentService.addDepartment(idCounter, name);
            System.out.println("Department added successfully.");
            idCounter++;
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Error while adding department" + e);
        }
    }

    /**
    * <p>
    * deletes the department using the departmentId.
    * </p>
    */
    public void deleteDepartment() {
        try {
            System.out.print("Enter Department ID: ");
            int id = scanner.nextInt();

            departmentService.removeDepartment(id);
            System.out.println("Department deleted successfully.");
        } 
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Error while deleting department" + e);
        }
    }

    /**
    * <p>
    * display all departments in database and it is called during the add
    * employee method to show and get input from user.
    * </p>
    */
    public void displayAllDepartments() {
        try {
            System.out.printf("%-10s %-20s\n","ID","Department Name");
            System.out.println("------------------------------");
            departmentService.getAllDepartments().forEach(System.out::println);
            System.out.println("------------------------------");
        } catch (DatabaseException e) {
            System.out.println("No department found" + e);
        }
    }

/**
 * prompts the user to enter details for an existing department ID and updates its information.
 */
    public void updateDepartment() {
        try {
            System.out.print("Enter the ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();

            departmentService.updateDepartment(id, name);
            System.out.println("Department updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Error while updating department" + e);
        }
    }

    /**
     * <p>
     * User to enter the ID of a department and displays the employees
     * in it.
     * </p>
     */
    public void displayEmployeesInDepartment() {
        try {
             displayAllDepartments();
             System.out.println("enter Department ID: ");
             int id=scanner.nextInt();
             scanner.nextLine();

             List<Employee> employees = departmentService.getEmployeesByDepartmentId(id);
             System.out.printf("Employees in Department %d:\n",id);
             System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s\n", "EmployeeID", "Name", "Age", "Department", "Email", "Mobile");
             System.out.println("-----------------------------------------------------------------------------------------------------------------");
             for(Employee employee : employees) {
                 System.out.println(employee);
             }
             System.out.println("-----------------------------------------------------------------------------------------------------------------");
         } 

        catch(IllegalArgumentException e){
              System.out.println(e.getMessage());
         } catch (DatabaseException e) {
            System.out.println("Department not found" + e);
        }
    }
}
