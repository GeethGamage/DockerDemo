package com.stackstitch.docker.dto;

import com.stackstitch.docker.entity.Orders;
import com.stackstitch.docker.entity.Product;
import com.stackstitch.docker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AggregateDto {

    private List<User> userList;
    private List<Orders> orderList;
    private List<Product> productList;
}
