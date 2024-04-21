package com.project.retail.product.controller;

import com.project.retail.model.entity.Product;
import com.project.retail.model.entity.dto.ProductDto;
import com.project.retail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public String create(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping(value = "/addStock/{id}")
    public String addStock(@PathVariable("id") Long id, @RequestParam("stock") Integer stock) {
        return productService.addStock(id, stock);
    }

    @PutMapping(value = "/reduceStock/{id}")
    public String reduceStock(@PathVariable("id") Long id, @RequestParam("stock") Integer stock) {
        return productService.reduceStock(id, stock);
    }

    @PutMapping(value = "/price/{id}")
    public String updatePrice(@PathVariable("id") Long id, @RequestParam("price") Double price) {
        return productService.updatePrice(id, price);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping(value = "/{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @GetMapping(value = "/all")
    public List<Product> findAll() {
        return productService.getProducts();
    }

    @GetMapping(value = "/page")
    public Page<Product> findByPage(@RequestParam Integer index, @RequestParam Integer size) {
        return productService.getProductsByPage(index, size);
    }
}
