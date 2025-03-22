package com.shrajan.Order.Service;

import com.shrajan.Order.Entity.Order;
import com.shrajan.Order.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {
    @Autowired
    private OrderRepository repo;

    public Order createOrder(Order order) {
        try{
            return repo.save(order);
        }
        catch (Exception e){
            return null;
        }
    }
}
