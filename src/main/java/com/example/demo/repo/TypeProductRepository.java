package com.example.demo.repo;

import com.example.demo.models.*;
import org.springframework.data.repository.CrudRepository;

public interface TypeProductRepository extends CrudRepository<TypeProduct, Long>
{
    TypeProduct findTypeProductById(Long id);
    TypeProduct findByTypeProductName(String typeProductName);
}
