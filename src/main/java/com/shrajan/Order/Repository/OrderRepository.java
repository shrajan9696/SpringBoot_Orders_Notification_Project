package com.shrajan.Order.Repository;

import com.shrajan.Order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
