package com.shrajan.order.controller;
import com.shrajan.order.entity.Notification;
import com.shrajan.order.entity.Order;
import com.shrajan.order.service.OrderService;
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
import java.util.logging.Logger;

@RestController
public class OrderController {

    private final WebClient.Builder webClientBuilder;
    private final OrderService orderService;
    Logger logger = Logger.getLogger(getClass().getName());

    // in future we will be using Kafka message broker
    public OrderController(WebClient.Builder webClientBuilder, OrderService orderService) {
        this.webClientBuilder = webClientBuilder;
        this.orderService = orderService;
    }


    @PostMapping("/createOrder")
    public Mono<ResponseEntity<Map<String, Object>>> createOrder(@RequestBody Order order) {
        logger.info("create order method started");
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





