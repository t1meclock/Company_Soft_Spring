package com.example.demo.repo;

import com.example.demo.models.*;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Long>
{
    Inventory findInventoryById(Long id);
    Inventory findByInventoryName(String inventoryName);
    Inventory findByInventoryNumber(String inventoryNumber);
}
