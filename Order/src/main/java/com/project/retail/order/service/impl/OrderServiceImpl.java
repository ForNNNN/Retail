package com.project.retail.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.retail.model.entity.Inventory;
import com.project.retail.model.entity.Product;
import com.project.retail.model.entity.dto.ProductInfo;
import com.project.retail.model.entity.dto.Transaction;
import com.project.retail.order.repository.OrderRepository;
import com.project.retail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.project.retail.model.entity.Order;
import com.project.retail.model.entity.User;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${application.port.inventory}")
    private String inventoryPort;

    @Value("${application.port.product}")
    private String productPort;

    @Value("${application.port.user}")
    private String userPort;

    public String placeOrder(ProductInfo productInfo){
        Integer quantity = productInfo.getQuantity();
        Long productId = productInfo.getProductId();
        Long userId = productInfo.getUserId();

        Integer stock = null;
        try {
            ResponseEntity<Integer> response = restTemplate.getForEntity(String.format("http://localhost:%s/inventory/checkStock/"+productId, inventoryPort), Integer.class);
            stock = response.getBody();
        } catch (Exception e) {
            return "the product doesn't exist";
        }

        //获得product记录
        Product product = restTemplate.getForObject(String.format("http://localhost:%s/product/" + productId, productPort), Product.class);
        //获得user记录
        User user = restTemplate.getForObject(String.format("http://localhost:%s/user/" + userId, userPort), User.class);
        if (user==null) return "the user doesn't exist";

        if (stock>=quantity){
            //如果成功则result为success;失败则为failed
            ResponseEntity<String> result = restTemplate.getForEntity(String.format("http://localhost:%s/inventory/reduce/%d/%d", inventoryPort, productId, quantity), String.class);
            if (result.getBody().equals("success")){
                //生成order记录
                Order order = new Order();

                order.setProduct(product);
                order.setUser(user);
                order.setQuantity(quantity);
                Double totalPrice = product.getUnitPrice()*quantity;
                order.setTotalPrice(totalPrice);
                order.setUnitPrice(product.getUnitPrice());
                order.setOrderTime(new Timestamp(System.currentTimeMillis()));

                product.setOrderList(Set.of(order));
                user.setOrderList(Set.of(order));
                orderRepository.save(order);

                Transaction transaction = new Transaction();
                transaction.setProductId(productId);
                transaction.setUserId(userId);
                transaction.setTotalPrice(totalPrice);

                //TODO: 给kafka队列发送
//                try {
//                    kafkaTemplate.send("placeOrder",objectMapper.writeValueAsString(transaction));
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
                return "success";
            }
        }
        return "inventory shortage";
    }
}
