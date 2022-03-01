package ru.novikova.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novikova.spring.market.entities.OrderItem;
import ru.novikova.spring.market.repositories.OrderItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public Optional<OrderItem> findById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
