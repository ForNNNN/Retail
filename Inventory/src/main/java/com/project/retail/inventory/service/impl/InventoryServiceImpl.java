package com.project.retail.inventory.service.impl;

import com.project.retail.inventory.repository.InventoryRepository;
import com.project.retail.inventory.service.InventoryService;
import com.project.retail.model.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public String reduceInventory(Long productId, Integer quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        if (inventory.isPresent() && inventory.get().getStock()>=quantity){
            inventoryRepository.decreaseStockByProductId(quantity,productId);
            return "success";
        }
        return "failed";
    }

    @Override
    public Integer checkStock(Long productId) throws Exception{
        return inventoryRepository.findByProductId(productId).get().getStock();
    }
}
