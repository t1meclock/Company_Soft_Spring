package com.example.demo.repo;

import com.example.demo.models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilterUserRepository extends CrudRepository<User, Long> {
    List<User> findByUsername(String username);

    List<User> findByUsernameContains(String username);
}