package com.example.demo.repo;

import com.example.demo.models.*;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long>
{
    Client findClientById(Long id);
    Client findByOrgName(String orgName);
    Client findByOrgAddress(String orgAddress);
}
