package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "typeproduct_table")
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не должно быть пустым")
    private String typeProductName;

    @OneToMany(mappedBy = "typeProduct", fetch = FetchType.EAGER)
    public List<Product> products = new ArrayList<>();

    @OneToOne(optional = true, mappedBy = "typeProduct")
    private Order order;

    //

    public TypeProduct(String typeProductName) {
        this.typeProductName = typeProductName;
    }

    public TypeProduct() {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}