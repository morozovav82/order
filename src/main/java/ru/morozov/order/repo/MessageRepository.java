package ru.morozov.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.order.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySentIsNullOrderById();
}
