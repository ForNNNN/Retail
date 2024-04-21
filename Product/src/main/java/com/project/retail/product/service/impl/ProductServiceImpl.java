package com.project.retail.product.service.impl;

import com.project.retail.model.entity.Inventory;
import com.project.retail.model.entity.Product;
import com.project.retail.model.entity.dto.ProductDto;
import com.project.retail.product.repository.ProductRepository;
import com.project.retail.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String createProduct(ProductDto productDto) {
        if (productRepository.findByName(productDto.getName()).isEmpty()){
            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);

            Inventory inventory = new Inventory();
            inventory.setStock(productDto.getStock());
            inventory.setProduct(product);
            product.setInventory(inventory);

            productRepository.save(product);
            return "product created successfully";
        }
        return "the product already existed";
    }

    @Override
    public String addStock(Long id, Integer stock) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Integer cur_stock = product.get().getInventory().getStock();
            product.get().getInventory().setStock(cur_stock + stock);
            productRepository.save(product.get());
            return "product's stock updated successfully";
        } else {
            return "product not found";
        }
    }

    @Override
    public String updatePrice(Long id, Double price) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setUnitPrice(price);
            productRepository.save(product.get());
            return "product's price updated successfully";
        } else {
            return "product not found";
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Page<Product> getProductsByPage(Integer index, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(index, pageSize);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public String reduceStock(Long id, Integer stock) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Integer cur_stock = product.get().getInventory().getStock();
            if (cur_stock>=stock){
                product.get().getInventory().setStock(cur_stock-stock);
                productRepository.save(product.get());
                return "product's stock updated successfully";
            }else {
                return "the current stock is greater than the stock to be reduced";
            }
        } else {
            return "product not found";
        }
    }

}
