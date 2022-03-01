package ru.novikova.spring.market.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.novikova.spring.market.dtos.OrderItemDto;
import ru.novikova.spring.market.entities.OrderItem;
import ru.novikova.spring.market.exceptions.ResourceNotFoundException;
import ru.novikova.spring.market.repositories.OrderRepository;
import ru.novikova.spring.market.services.OrderService;
import ru.novikova.spring.market.services.ProductService;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {
    private final ProductService productService;
    private final OrderService orderService;

    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setProduct(productService.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(("Продукт " + orderItemDto.getProductId() + " не найден"))));
        orderItem.setOrder(orderService.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException(("Заказ " + orderItemDto.getOrderId() + " не найден"))));
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPricePerProduct(orderItemDto.getPricePerProduct());
        orderItem.setPrice(orderItemDto.getPrice());
        return orderItem;
    }
}
