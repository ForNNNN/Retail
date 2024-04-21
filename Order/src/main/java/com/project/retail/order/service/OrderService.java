package com.project.retail.order.service;

import com.project.retail.model.entity.dto.ProductInfo;

public interface OrderService {
    public String placeOrder(ProductInfo productInfo);
}
