package com.stackstitch.docker.service.Impl;

import com.stackstitch.docker.dto.AggregateDto;
import com.stackstitch.docker.entity.Orders;
import com.stackstitch.docker.entity.Product;
import com.stackstitch.docker.entity.User;
import com.stackstitch.docker.repository.OrdersRepository;
import com.stackstitch.docker.repository.ProductRepository;
import com.stackstitch.docker.repository.UserRepository;
import com.stackstitch.docker.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AggregatorServiceImpl implements AggregatorService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductRepository productRepository;

    public AggregateDto getAllList() throws Exception{

        CompletableFuture<List<User>> userList = CompletableFuture.supplyAsync(() ->
        {
            return userRepository.findAll();
        });

        CompletableFuture<List<Orders>>  orderList = CompletableFuture.supplyAsync(() ->
        {
            return ordersRepository.findAll();
        });

        CompletableFuture<List<Product>> productList = CompletableFuture.supplyAsync(() ->
        {
            return productRepository.findAll();
        });

        return  CompletableFuture.allOf(userList, productList, orderList).
                thenApply(v -> new AggregateDto(userList.join(), orderList.join(), productList.join())).get();
    }
}
