//package ru.novikova.spring.market.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import ru.novikova.spring.market.entities.Order;
//import ru.novikova.spring.market.entities.User;
//import ru.novikova.spring.market.exceptions.ResourceNotFoundException;
//import ru.novikova.spring.market.services.OrderService;
//import ru.novikova.spring.market.services.UserService;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/api/v1/orders")
//@RequiredArgsConstructor
//public class OrderController {
//    private final UserService userService;
//    private final OrderService orderService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Order createOrder(Principal principal /*, @RequestBody OrderData orderData */) {
//        User user = userService.findByUsername(principal.getName())
//                .orElseThrow(() -> new ResourceNotFoundException("Пользователь " + principal.getName() + " не найден"));
//        return orderService.createOrder(user);
//    }
//}
