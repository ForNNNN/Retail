package com.project.retail.inventory.service;

public interface InventoryService {

    String reduceInventory(Long productId, Integer quantity);

    Integer checkStock(Long productId) throws Exception;
}
