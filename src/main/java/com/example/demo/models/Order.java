package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String typeProductName;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String productName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client")
    private Client client;

    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_product_id")
    private TypeProduct typeProduct;

    private String deadline;

    //

    public Order(String typeProductName, String productName,
                 Client client) {
        this.typeProductName = typeProductName;
        this.productName = productName;
        this.client = client;
    }

    public Order() {
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeProductName() {
        return typeProductName;
    }

    public void setTypeProductName(String typeProductName) {
        this.typeProductName = typeProductName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
}