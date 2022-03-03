package ru.novikova.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novikova.market.core.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
