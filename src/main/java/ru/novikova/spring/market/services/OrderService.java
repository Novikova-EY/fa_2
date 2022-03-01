package ru.novikova.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import ru.novikova.spring.market.converters.OrderItemConverter;
import ru.novikova.spring.market.dtos.OrderDto;
import ru.novikova.spring.market.dtos.OrderItemDto;
import ru.novikova.spring.market.entities.Order;
import ru.novikova.spring.market.entities.OrderItem;
import ru.novikova.spring.market.entities.User;
import ru.novikova.spring.market.exceptions.ResourceNotFoundException;
import ru.novikova.spring.market.repositories.OrderItemRepository;
import ru.novikova.spring.market.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;
//    private final OrderItemConverter orderItemConverter;

    public Order createOrder(User user) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(user);

        List<OrderItemDto> orderItemsToDto = cartService.getCurrentCart().getItems()
                .stream()
                .map(cartItem -> new OrderItemDto(
                        null,
                        cartItem.getProductId(),
//                        orderDto.getId(),
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()))
                .collect(Collectors.toList());

        orderDto.setItems(orderItemsToDto);
        int totalPrice = 0;
        for (OrderItemDto orderItem : orderItemsToDto) {
            totalPrice += orderItem.getQuantity() * orderItem.getPricePerProduct();
        }
        orderDto.setTotalPrice(totalPrice);

        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUser(orderDto.getUser());
        List<OrderItemDto> orderItemDtos = orderDto.getItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemDto orderItemDto : orderItemDtos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemDto.getId());
            orderItem.setProduct(productService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Не удается добавить продукт с id: " + orderItemDto.getProductId())));
            // orderDto.getId() = null ????
            orderItem.setOrder(orderRepository.findById(orderDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Не удается найти заказ с id: " + orderDto.getId())));
            orderItem.setPrice(orderItemDto.getPrice());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        order.setTotalPrice(orderDto.getTotalPrice());
        orderRepository.save(order);
        orderItems.stream().map(orderItemRepository::save);
        return order;
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
