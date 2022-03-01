package ru.novikova.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novikova.spring.market.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
