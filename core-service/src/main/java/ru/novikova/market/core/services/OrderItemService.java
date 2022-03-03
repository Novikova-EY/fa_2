package ru.novikova.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novikova.market.core.entities.OrderItem;
import ru.novikova.market.core.repositories.OrderItemRepository;

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
