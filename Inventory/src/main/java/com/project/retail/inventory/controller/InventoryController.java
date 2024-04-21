package com.project.retail.inventory.controller;

import com.project.retail.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/inventory")
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/checkStock/{productId}")
    public Integer checkStock(@PathVariable(name = "productId") Long productId) throws Exception {
        return inventoryService.checkStock(productId);
    }

    @GetMapping("/reduce/{productId}/{quantity}")
    public String reduce(@PathVariable(name = "productId") Long productId, @PathVariable(name = "quantity") Integer quantity){
        return inventoryService.reduceInventory(productId,quantity);
    }

}
