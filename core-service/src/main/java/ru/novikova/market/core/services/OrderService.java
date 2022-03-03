package ru.novikova.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.novikova.market.api.dtos.CartDto;
import ru.novikova.market.api.exceptions.ResourceNotFoundException;

import ru.novikova.market.core.entities.Order;
import ru.novikova.market.core.entities.OrderItem;
import ru.novikova.market.core.entities.User;
import ru.novikova.market.core.integration.CartServiceIntegration;
import ru.novikova.market.core.repositories.OrderRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public void createOrder(User user) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart()
                .orElseThrow(() -> new ResourceNotFoundException("Корзина пустая"));

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cartDto.getTotalPrice());

        order.setItems(cartDto.getItems()
                .stream()
                .map(cartItem -> new OrderItem(
                        productService.findById(cartItem.getProductId()).get(),
                        order,
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()))
                .collect(Collectors.toList()));

        orderRepository.save(order);
        cartServiceIntegration.clear();
    }
}
