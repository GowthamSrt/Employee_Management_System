package com.project.service;

import java.util.ArrayList;
import java.util.List;

import com.project.dao.ProjectRepositoryImpl;
import com.model.Project;
import exception.DatabaseException;

/**
* <p>
* Implementing the project related services which used to add, update,
* delete and display the projects in database by using the proejct Id
* </p>
*/
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepositoryImpl projectRepository  = new ProjectRepositoryImpl();

    /**
    * <p>
    * Add method passes the parameters from controller to dao to add it
    * in the database.
    * </p>
    * @param projectName
    */
    public void addProject(int id, String name) throws DatabaseException {
        Project project = new Project(id, name);
        projectRepository.addProject(project);
    }

    /**
    * <p>
    * delete the projects if exist and it will not delete if any employee 
    * assigned to a project.
    * </p>
    */
    public void removeProject(int id) throws IllegalArgumentException, DatabaseException {
        Project project = projectRepository.findProjectById(id);
        if (project != null) {
            projectRepository.deleteProject(id);
        } else {
            throw new IllegalArgumentException("Project not found" + id);
        }
    }

    /**
    * <p>
    * Returns all the projects from the project class and it is used to 
    * display the project while assigning to the employees.
    * </p>
    */  
    public List<Project> getAllProjects() throws DatabaseException {
        return projectRepository.getAllProjects();
    }
    
    /**
    * <p>
    * Returns the projectId which is used to connect with database to any
    * any updates required.
    * </p>
    */
    public Project getProjectById(int id) throws DatabaseException {
        return projectRepository.findProjectById(id);
    }

    /**
    * <p>
    * Update method updates the Id and name of the project only if already
    * exists.
    * </p>
    */
    public void updateProject(int id, String name) throws IllegalArgumentException, DatabaseException {
        Project project = projectRepository.findProjectById(id);
        if (project != null) {
            project.setName(name);
            projectRepository.updateProject(project);
        } else {
            throw new IllegalArgumentException("Project not found" + id);
        }
    }
}
