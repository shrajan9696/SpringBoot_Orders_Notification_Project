package com.shrajan.order.service;

import com.shrajan.order.entity.Order;
import com.shrajan.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    private final OrderRepository repo;

    OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Order createOrder(Order order) {
        logger.info("create order method statrted");
        try{
            return repo.save(order);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Order> getAllOrders() {
        return repo.findAll();
    }
}
