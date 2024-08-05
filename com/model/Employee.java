package com.model;

import java.time.LocalDate;
import java.time.Period;
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

import com.model.Department;
import com.model.Project;

/**
*<p>
* Class initialize all the Employee parameter inputs to a List 
* which holds all employees details and used to get and show all the details
* of an employee.
* </p>
*/

@Entity
@Table (name = "employee")
public class Employee {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column (name = "name")
    private String name;
	
	@Column (name = "dob")
    private LocalDate dob;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
    private Department department;
	
	@Column (name = "email_id")
    private String emailId;
	
	@Column (name = "mobile")
    private String mobile;
	
	@Column (name = "is_active")
    private boolean isActive;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable (
				name = "employee_project",
				joinColumns = @JoinColumn(name = "employee_id"),
				inverseJoinColumns = @JoinColumn(name = "project_id")
				)
				
    private List<Project> project = new ArrayList<>();

    public Employee() {
    }

    public Employee(int id, String name, LocalDate dob, Department department, 
                    String emailId, String mobile) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.department = department;
        this.emailId = emailId;
        this.mobile = mobile;
        this.isActive = true;
        
    }
    
    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }  

    private int calculateAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public int getAgeInMonths() {
        return Period.between(dob, LocalDate.now()).getMonths();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public List<Project> getProjects() {
        return project;
    }

    public void setProjects(List<Project> project) {
        this.project = project;
    }

    public String toString() {
        return String.format("%-10s %-20s %-15s %-20s %-25s %-15s\n",id,
                             name, calculateAge() + "y" + getAgeInMonths() + "m", 
                             department.getName(), emailId, mobile);
    }
}
