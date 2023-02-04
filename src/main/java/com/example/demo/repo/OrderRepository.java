package com.example.demo.repo;

import com.example.demo.models.*;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{
    Order findOrderByClientId(Long id);
}