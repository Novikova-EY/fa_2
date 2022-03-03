package ru.novikova.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novikova.market.core.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
