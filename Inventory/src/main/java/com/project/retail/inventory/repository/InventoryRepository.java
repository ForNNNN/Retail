package com.project.retail.inventory.repository;

import com.project.retail.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    @Modifying
    @Transactional
    @Query(value = "update Inventory i set i.stock = i.stock- :quantity where i.id = :productId")
    void decreaseStockByProductId(@Param("quantity") Integer quantity, @Param("productId") Long productId);

    Optional<Inventory> findByProductId(Long productId);

}
