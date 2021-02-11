package ru.morozov.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOneByIdAndStatus(Long orderId, String status);
    List<Order> findByStatus(String status);
}
