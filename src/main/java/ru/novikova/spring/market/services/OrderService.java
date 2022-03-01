//package ru.novikova.spring.market.services;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.novikova.spring.market.converters.OrderItemConverter;
//import ru.novikova.spring.market.dtos.OrderItemDto;
//import ru.novikova.spring.market.entities.Order;
//import ru.novikova.spring.market.entities.User;
//import ru.novikova.spring.market.repositories.OrderRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final CartService cartService;
//    private final OrderItemConverter orderItemConverter;
//
//    public Order createOrder(User user) {
//        Order order = new Order();
//        order.setUser(user);
//
//        List<OrderItemDto> orderItems = cartService.getCurrentCart().getItems()
//                .stream()
//                .map(cartItem -> new OrderItemDto(
//                        null,
//                        cartItem.getProductId(),
//                        order.getId(),
//                        cartItem.getQuantity(),
//                        cartItem.getPricePerProduct(),
//                        cartItem.getPrice()))
//                .collect(Collectors.toList());
//
//        order.setItems(orderItems.stream().map(orderItemConverter::dtoToEntity).collect(Collectors.toList()));
//        int totalPrice = 0;
//        for (OrderItemDto orderItem : orderItems) {
//            totalPrice += orderItem.getQuantity() * orderItem.getPricePerProduct();
//        }
//        order.setTotalPrice(totalPrice);
//
//        return orderRepository.save(order);
//    }
//
//    public Optional<Order> findById(Long orderId) {
//        return orderRepository.findById(orderId);
//    }
//}
