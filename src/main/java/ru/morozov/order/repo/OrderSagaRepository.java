package ru.morozov.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.order.entity.OrderSaga;

import java.util.Optional;

public interface OrderSagaRepository extends JpaRepository<OrderSaga, Long> {
    Optional<OrderSaga> findOneByOrderIdAndStep(Long orderId, String step);
}
