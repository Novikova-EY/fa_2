package ru.novikova.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.novikova.spring.market.dtos.Cart;
import ru.novikova.spring.market.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping("/change/increaseProductQuantityInCart/{id}")
    public void increaseProductQuantityInCart(@PathVariable Long id) {
        cartService.increaseProductQuantityInCart(id);
    }

    @GetMapping("/change/decreaseProductQuantityInCart/{id}")
    public void decreaseProductQuantityInCart(@PathVariable Long id) {
        cartService.decreaseProductQuantityInCart(id);
    }

    @GetMapping("/change/deleteProductFromCart/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.deleteProductFromCart(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }
}
