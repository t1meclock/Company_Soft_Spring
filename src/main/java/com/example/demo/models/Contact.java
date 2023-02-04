package com.example.demo.models;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contact_table")
public class Contact
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Email(message = "Строка должна являться Email")
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @OneToOne(optional = true, mappedBy = "contact")
    private User user;

    //

    public Contact(String email, String phoneNumber, User user)
    {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public Contact() {}

    //

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}