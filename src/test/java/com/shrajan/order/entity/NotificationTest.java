package com.shrajan.order.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
 class NotificationTest {
    @Test
    void testNotification() {
        Notification notification = new Notification();
        notification.setNotificationId(1);
        notification.setNotificationMessage("Order Placed Successfully");
        notification.setOrderId(1);
        assertEquals(1, notification.getNotificationId());
        assertEquals("Order Placed Successfully", notification.getNotificationMessage());
        assertEquals(1, notification.getOrderId());
    }
}
