package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "product_table")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String productName;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String dateEnd;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String orgName;

    @ManyToOne()
    @JoinColumn(name = "type_product_id")
    public TypeProduct typeProduct;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    @OneToOne(optional = true, mappedBy = "product")
    private Report report;
//    @OneToOne(optional = true, mappedBy = "product")
//    private Order order;

    //

    public Product(String productName, String dateEnd, String orgName, TypeProduct typeProduct,
                   Inventory inventory, Order order)
    {
        this.productName = productName;
        this.dateEnd = dateEnd;
        this.orgName = orgName;
        this.typeProduct = typeProduct;
        this.inventory = inventory;
        //this.order = order;
    }

    public Product() {}

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
}