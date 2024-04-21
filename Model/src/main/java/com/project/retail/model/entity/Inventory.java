package com.project.retail.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "inventory_tb")
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock")
    private Integer stock;

    @JoinColumn(name = "product_id",referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Product product;

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", stock=" + stock +
                ", product=" + product +
                '}';
    }
}
