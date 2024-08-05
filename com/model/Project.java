package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import com.model.Employee;

/**
 * Represnts a project that can be associated with employees.
 * A project has a unique Identifier, a name and a list of employees
 * who participate in it.
 */
 
 @Entity
 @Table (name = "project")
public class Project implements Serializable {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column (name = "name", unique = true)
    private String projectName;
	
	@ManyToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employees = new ArrayList<>();
	
	
	public Project() {}
    
    /**
     * Constructs a project with the specified id and name.
     */
	
    public Project(int id, String name) {
       this.id = id;
       this.projectName = name; 
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return projectName;
    }
    
    public void setName(String name) {
        this.projectName = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
     
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s",id,projectName);
    }
}

     

  

