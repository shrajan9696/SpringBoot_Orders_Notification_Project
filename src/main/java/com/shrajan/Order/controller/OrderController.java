package com.shrajan.Order.controller;

import com.shrajan.Order.Entity.Notification;
import com.shrajan.Order.Entity.Order;
import com.shrajan.Order.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    private final WebClient.Builder webClientBuilder;

    public OrderController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Mono<ResponseEntity<Map<String, Object>>> createOrder(@RequestBody Order order) {
        System.out.println(order);
        System.out.println(order.getOrderName());

        Order createdOrder = orderService.createOrder(order);

        if (createdOrder == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to create order");
            return Mono.just(ResponseEntity.badRequest().body(error));
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("order", createdOrder);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("orderId", createdOrder.getOrderId());


        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8085/sendNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Notification.class)
                .map(notification -> {
                    responseMap.put("notification", notification);
                    responseMap.put("isNotificationReceived", true);
                    return ResponseEntity.ok(responseMap);
                })
                .onErrorResume(error -> {
                    responseMap.put("notificationError", "Failed to send notification: " + error.getMessage());
                    responseMap.put("isNotificationReceived", false);
                    return Mono.just(ResponseEntity.ok(responseMap));
                });
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<Object> getAllOrders() {
        try{
            return ResponseEntity.ok(orderService.getAllOrders());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("No orders found");
        }
    }

}





