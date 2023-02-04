package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "staff_table")
public class Staff
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @OneToOne(optional = true, mappedBy = "staff")
//    private User user;

    @OneToOne(optional = true, mappedBy = "staff")
    private Department department;

    private long departmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable (name="post_staff",
            joinColumns=@JoinColumn (name="staff_id"),
            inverseJoinColumns=@JoinColumn(name="post_id"))
    private Post post;

    private long postId;



    //

//    public Staff(User user, Role role)
//    {
//        this.user = user;
//    }

    public Staff() {}

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getPostId() { return postId; }

    public void setPostId(long postId) { this.postId = postId; }



    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public long getDepartmentId() { return departmentId; }

    public void setDepartmentId(long departmentId) { this.departmentId = departmentId; }
}