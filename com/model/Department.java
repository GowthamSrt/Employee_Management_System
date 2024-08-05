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
import javax.persistence.OneToMany;

import com.model.Employee;

/**
 * <p>
 * The Department class represents a department entity in the organization.
 * It contains details such as the department ID, name
 * </p>
 */
 
 @Entity
 @Table(name = "department")
public class Department implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "name", unique = true)
    private String name;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Department () {} 

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        this.employees = new ArrayList<>();
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
    public List<Employee> getEmployees(){
        return employees;
    } 
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
       this.employees.add(employee);
    }

    @Override
    public String toString() {
       return String.format("%-10s %-20s",id,name);
    }
}
