package com.example.demo.repo;

import com.example.demo.models.*;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>
{
    Post findPostById(Long id);
    Post findPostByPostName(String postName);
}
