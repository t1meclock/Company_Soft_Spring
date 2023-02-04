package com.example.demo.repo;

import com.example.demo.models.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Long>
{
    Contact findByEmail(String email);
    Contact findByPhoneNumber(String phoneNumber);
}
