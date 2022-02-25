package ru.novikova.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novikova.spring.market.entities.Order;
import ru.novikova.spring.market.entities.OrderItem;
import ru.novikova.spring.market.entities.User;
import ru.novikova.spring.market.exceptions.ResourceNotFoundException;
import ru.novikova.spring.market.model.CartItem;
import ru.novikova.spring.market.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public Order createOrder(User user) {
        Order order = new Order();

        List<OrderItem> orderItems = cartService.getCurrentCart().getItems()
                .stream()
                .map(cartItem -> new OrderItem(
                        productService.findById(cartItem.getProductId())
                                .orElseThrow(() -> new ResourceNotFoundException("Не удается найти продукт с id: " + cartItem.getProductId())),
                        cartItem.getQuantity()
                )).collect(Collectors.toList());

        order.setItems(orderItems);
        order.setUser(user);
        order.setTotalPrice(cartService.getCurrentCart().getTotalPrice());

        orderRepository.save(order);
        return order;
    }
}
