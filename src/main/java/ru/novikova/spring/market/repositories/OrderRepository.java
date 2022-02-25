package ru.novikova.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novikova.spring.market.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
