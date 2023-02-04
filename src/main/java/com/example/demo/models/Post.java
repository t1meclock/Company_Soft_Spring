package com.example.demo.models;

import com.example.demo.models.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "post_table")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String postName;
    private int salary;

    @ManyToMany
    @JoinTable (name="department_post",
            joinColumns=@JoinColumn (name="post_id"),
            inverseJoinColumns=@JoinColumn(name="department_id"))
    public Set<Department> departments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable (name="post_staff",
            joinColumns=@JoinColumn (name="post_id"),
            inverseJoinColumns=@JoinColumn(name="staff_id"))
    public List<Staff> staff = new ArrayList<>();

    //

    public Post() {}

    public Post(String postName, int salary)
    {
        this.postName = postName;
        this.salary = salary;
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public List<Staff> getStaff() { return staff; }

    public void setStaff(List<Staff> staffs) { this.staff = staffs; }
}