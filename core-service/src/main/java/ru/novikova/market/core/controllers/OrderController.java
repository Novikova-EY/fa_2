package ru.novikova.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.novikova.market.api.exceptions.ResourceNotFoundException;
import ru.novikova.market.core.entities.User;
import ru.novikova.market.core.services.OrderService;
import ru.novikova.market.core.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal /*, @RequestBody OrderData orderData */) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь " + principal.getName() + " не найден"));
        orderService.createOrder(user);
    }
}
