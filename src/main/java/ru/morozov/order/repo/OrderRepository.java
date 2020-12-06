package ru.morozov.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
