package com.project.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.model.Employee;
import com.model.Project;
import com.project.service.ProjectService;
import com.project.service.ProjectServiceImpl;
import com.exception.DatabaseException;


/**
* <p>
* To handle the project related operations of add, update, delete and
* display the projects based on user request.
* </p>
*/
public class ProjectController {
    private ProjectService projectService = new ProjectServiceImpl();
    private static int idCounter = 1;
    private static final Logger logger = LogManager.getLogger(ProjectController.class);
    private Scanner scanner = new Scanner(System.in);


    /**
     * Displays the menu for project managemnt and processes user input.
     */
    public void displayProjectManagement() {
		try {
			while (true) {
				System.out.println("Project Management");
				System.out.println("------------------------------");
				System.out.println("1) Add Project");
				System.out.println("2) Delete Project");
				System.out.println("3) Display All Projects");
				System.out.println("4) Display Project by ID");
				System.out.println("5) Update Project");
				System.out.println("6) Delete Project");
				System.out.println("7) Display Employees In Project");
				System.out.println("8) Back to Main Menu");
				System.out.println("------------------------------");
				System.out.print("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
					case 1:
						createProject();
						break;
					case 2:
						deleteProject();
						break;
					case 3:
						displayAllProjects();
						break;
					case 4:
						displayProjectById();
						break;
					case 5:
						updateProject();
						break;
					case 6:
						deleteProject();
						break;
					case 7:
						displayEmployeesInProject();
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
    * <p>
    * Get the project Id from end user and checks for whether the project Id
    * already in Database. If not, then allows to add new project.
    *</p>
    * @param projectName
    */
    public void createProject() {
        try {
            System.out.print("Enter project Name: ");
            String name = scanner.nextLine();

            projectService.addProject(idCounter, name);
            logger.info("Project added successfully.");
            idCounter++;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (DatabaseException e) {
            logger.error("Unable to add project" + e);
        }
    }

    /**
    * <p>
    * Delete the project from the Database by entering the specific project id
    * </p>
    */
    public void deleteProject() {
        try {
			displayAllProjects();
            System.out.print("Enter project Id to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            projectService.removeProject(id);
            logger.info("Project deleted successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (DatabaseException e) {
            logger.error("Unable to delete project" + e);
        }
    }

    /**
    * <p>
    * Display the projects available in database and used to get the projects
    * using Ids from users while adding the employees.
    *</p>
    */ 
    public void displayAllProjects() {
        try {
            System.out.println("All Projects");
            System.out.printf("%-15s %-15s\n", "ProjectID", "Project Name");
            System.out.println("------------------------------------");
            projectService.getAllProjects().forEach(System.out::println);
            System.out.println("------------------------------------");
        } catch (DatabaseException e) {
            logger.error("No projects to display" + e);
        }
    }
 
    /**
     * <p>
     * To view the specific project by using the project Id from database.
     * </p>
     */
    public void displayProjectById() {
        try {
            System.out.print("Enter project Id to display: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Project project = projectService.getProjectById(id);
            if (project != null) {
                System.out.printf("%-15s %-15s\n", "Project ID", "Project Name");
                System.out.println("------------------------------------");
                System.out.println(+project.getId()+ "               " + project.getName());
                System.out.println("------------------------------------");
            } else {
                System.out.println("Project not found." +id);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (DatabaseException e) {
            logger.error("Unable to show that project" + e);
        }
    }

   /**
    * <p>
    * Update the project details by getting the Id reference.
    *</p>
    */
    public void updateProject() {
        try {
            System.out.print("Enter project Id: ");
            int id = scanner.nextInt();
            if(projectService.getProjectById(id) == null) {
                System.out.println("Project ID Not Found" +id);
                return;
            }
            scanner.nextLine();
            System.out.print("Enter project Name: ");
            String name = scanner.nextLine();

            projectService.updateProject(id, name);
            logger.info("Project updated successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (DatabaseException e) {
            logger.error("Unable to update project" + e);
        }
    }

    /**
    * <p>
    * Display the projects available in database and used to get the projects using project Id.
    *</p>
    */
    public void displayEmployeesInProject() {
        try {
             displayAllProjects();
             System.out.println("enter Project ID: ");
             int id=scanner.nextInt();
             scanner.nextLine();
             List<Employee> employees = projectService.getProjectById(id).getEmployees();

             if (employees.isEmpty()) {
                 System.out.println("No employees in this project");
             }
             else {
                 System.out.printf("Employees in Projects %d:\n",id);
                 System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s\n",
                                   "Employee ID", "Name", "Age", "Department",
                                   "Email", "Mobile");
                 System.out.println("--------------------------------------------"
                                + "--------------------------------------------"
                                + "---------------------------------");
                 for(Employee employee : employees) {
                     System.out.println(employee);
                 }
                 System.out.println("--------------------------------------------"
                                   + "--------------------------------------------"
                                   + "---------------------------------");
                
             }
         } catch(IllegalArgumentException e){
              logger.error(e.getMessage());
        } catch (DatabaseException e) {
            logger.error("No employees found in this project" + e);
        }
    }
}