package com.shrajan.Order.Service;

import com.shrajan.Order.Entity.Order;
import com.shrajan.Order.Repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {
    @Autowired
    private OrderRepository repo;

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
