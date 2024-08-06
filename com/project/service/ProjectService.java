package com.project.service;

import java.util.ArrayList;
import java.util.List;

import com.project.dao.ProjectRepositoryImpl;
import com.model.Project;
import com.project.service.ProjectServiceImpl;
import com.exception.DatabaseException;

/**
 * <p>
 * Interface for ProjectService to  handle project related operation.
 * </p>
 */
public interface ProjectService {
    
    /**
     * Adds a new project to the repository.
     * @param id - The unique identifier for the project.
     * @param name - The name of the project
     */
    public void addProject(int id, String name) throws IllegalArgumentException, DatabaseException;

    /**
     * Remove a project from the repository by its Id.
     *
     * @param id - The unique identifier of the project to be removed.
     * @thows IllegalArgumentException if the project with the given ID is not found.
     */
    public void removeProject(int id) throws DatabaseException;

    /**
     * Retrieves all project from the repository.
     * @return A List of all project.
     */     
    public List<Project> getAllProjects() throws DatabaseException;

    /**
     * Retrieves a project by its unique identifier.
     *
     *@param id - The unique identifier of the project.
     *@return The project with the given ID, or null if not found.
     */
    public Project getProjectById(int id) throws DatabaseException;

    /**
     * Updates the details of an existing project.
     * @param id - the unique identifier of the project to the updated.
     * @param name - The new name for the project.
     * @thows IllegalArgumentException if the project with the given ID is not found.
     */
    public void updateProject(int id, String name) throws IllegalArgumentException, DatabaseException;

}


