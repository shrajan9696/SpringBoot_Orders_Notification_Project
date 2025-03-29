package com.shrajan.Order.Service;

import com.shrajan.Order.Entity.Order;
import com.shrajan.Order.Repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderName("Mobile");
        order.setOrderPrice(20000);
        when(orderRepository.findAll()).thenReturn(new ArrayList<>(List.of(order)));
        assertNotNull(orderService.getAllOrders());
        List<Order> allOrders = orderService.getAllOrders();
        assertEquals(allOrders.get(0).getOrderId(),order.getOrderId());
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderName("Mobile");
        order.setOrderPrice(20000);
        when(orderRepository.save(order)).thenReturn(order);
        assertEquals(order, orderService.createOrder(order));
    }
}
