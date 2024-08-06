package com.project.dao;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Hibernate;

import com.model.Project;
import com.model.Employee;
import com.model.Department;
import com.exception.DatabaseException;
import com.utils.HibernateConnection;


/**
* <p>
* Class implements the project related operations and from here onwards
* all the project related operations are done as per the user request.
* </p>
*/
public class ProjectRepositoryImpl implements ProjectRepository {

    /**
    * <p>
    * Method which adds the details of projects taken from controller.
    * </p>
    * *@param projectName
    */
    public void addProject(Project project) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			session.save(project);
			transaction.commit();     

        } catch (HibernateException e) {
			if(transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException ("Error while adding project" + project.getName());
        } finally {
            session.close();
        }
    }


    /**
    * <p>
    * Method which updates the details of project taken from controller and
    * here updation only done if the project is already exist.
    * </p>
    */
    public void updateProject(Project project) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			Project existingProject = session.get(Project.class, project.getId());
			if (existingProject != null) {
			    existingProject.setName(project.getName());
				session.update(existingProject);
				transaction.commit();
			}
			else {
				throw new DatabaseException("No project found with ID : " + project.getId());
			}
        } catch (HibernateException e) {
            throw new DatabaseException ("Error while updating project : " + project.getId());
        } finally {
            session.close();
        }
    }

   /**
    * <p>
    * Delete the the project as per user request if and only if there is
    * no employee is assigned to that particular project.
    * </p>
    */
    public void deleteProject(int id) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        try {
			transaction = session.beginTransaction();
			 Project project = session.get (Project.class, id);
             if (project != null) {
                 session.delete(project);             
                 transaction.commit();
             }
             else {
                 throw new DatabaseException("Project not found!!!  " + id);
             }

        } catch (HibernateException e) {
             throw new DatabaseException ("No project found  " + id);
        }  finally {
            session.close();
        } 
    }

    /**
    * <p>
    * Returns the all the projectId and projectName from the list that we have
    * currently in.
    * </p>
    */
    public List<Project> getAllProjects() throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
        List<Project> projects = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            projects = session.createQuery("from Project",Project.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Unable to show projects" + e);
        } finally {
            session.close();
        }
        return projects;
    }

    /**
     * To finds a project by ID from the database.
     * @param id - project to find.
     * @return The project if found, null otherwise.
     */
    public Project findProjectById(int id) throws DatabaseException {
        Session session = HibernateConnection.getFactory().openSession();
        try {
            Project project = session.get(Project.class, id);
			if (project != null) {
			    return project;
			}
			else {
			    throw new DatabaseException("Project not found in this ID : " + id);
			}
            
        } catch (HibernateException e) {
            throw new DatabaseException ("No project found  " + id);
        } finally {
            session.close();

        }

    }


    /**
     * Maps a row from the resultSet to an project object.
     * @param resultSet The ResultSet containing the data.
     */
    public Project mapRowToProject(int id, String name) throws DatabaseException {
		Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;
		Project project = null;
        try {
			transaction = session.beginTransaction();
			project = session.get(Project.class, id);
			if (project == null) {
			    throw new DatabaseException("No project found with ID : " + id);
			}
			
			project.setName(name);
        } catch (HibernateException e) {
			if (transaction != null) {
			    transaction.rollback();
			}
            throw new DatabaseException ("No project found  " + id);
        } finally {
			session.close();
		}
		return project;
    }

    /**
     * Retrives the employee association with an projects by their id.
     * @param projectId - Id of project.
     * @return A list of employee association with projects.
     */
    public List<Employee> getEmployeesByProjectId(int projectId) throws DatabaseException {
        List<Employee> employees = new ArrayList<>();
        Session session = HibernateConnection.getFactory().openSession();
        Transaction transaction = null;

        try {
			transaction = session.beginTransaction();
			String hqlQuery = "select e from Employee e join e.projects p where p.id = :projectId and e.isActive = true";
			Query<Employee> query = session.createQuery(hqlQuery, Employee.class);
			query.setParameter("projectId", projectId);
			employees = query.list();
			transaction.commit();
         } catch (HibernateException e) {
			 if (transaction != null) {
			     transaction.rollback();
			 }
             throw new DatabaseException ("No employee found in this project with ID : " + projectId);
         } finally {
             session.close();
         } 

        return employees;
    } 

}
