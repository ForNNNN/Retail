package com.project.retail.model.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
    private String name;

    private String description;

    private Double unitPrice;

    private Integer stock;
}
