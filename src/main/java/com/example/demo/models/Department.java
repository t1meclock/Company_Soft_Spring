package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "department_table")
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String departmentName;

    @ManyToMany
    @JoinTable (name="department_post",
            joinColumns=@JoinColumn (name="department_id"),
            inverseJoinColumns=@JoinColumn(name="post_id"))
    public Set<Post> posts;

    @OneToOne(cascade = CascadeType.ALL)
    private Staff staff;

    //

    public Department() {}

    public Department (String departmentName)
    {
        this.departmentName = departmentName;
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Post> getPosts() { return posts; }

    public void setPosts(Set<Post> posts) { this.posts = posts; }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
