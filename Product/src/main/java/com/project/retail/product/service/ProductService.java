package com.project.retail.product.service;

import com.project.retail.model.entity.Product;
import com.project.retail.model.entity.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    String createProduct(ProductDto productDto);
    String updatePrice(Long id, Double price);
    void deleteProduct(Long id);
    String addStock(Long id, Integer quantity);
    List<Product> getProducts();
    Product getProduct(Long id);
    Page<Product> getProductsByPage(Integer index, Integer pageSize);
    String reduceStock(Long id, Integer stock);
}