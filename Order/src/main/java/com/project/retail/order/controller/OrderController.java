package com.project.retail.order.controller;

import com.project.retail.model.entity.dto.ProductInfo;
import com.project.retail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public String placeOrder(@RequestBody ProductInfo productInfo){
        return orderService.placeOrder(productInfo);
    }

}
