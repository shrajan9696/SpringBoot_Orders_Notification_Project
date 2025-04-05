package com.shrajan.order.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
 class OrderTest {

    @InjectMocks
    Order order;

    @Test
    void testOrder(){
        order.setOrderId(1);
        order.setOrderPrice(1);
        order.setOrderName("Mobile");
        assertEquals(1,order.getOrderId());
        assertEquals(1,order.getOrderPrice());
        assertEquals("Mobile",order.getOrderName());
    }
}
