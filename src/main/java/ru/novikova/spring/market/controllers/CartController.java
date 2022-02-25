package ru.novikova.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.novikova.spring.market.model.Cart;
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

    @GetMapping("/increase/{id}")
    public void increaseProductQuantityInCart(@PathVariable Long id) {
        cartService.increaseProductQuantityInCart(id);
    }

    @GetMapping("/decrease/{id}")
    public void decreaseProductQuantityInCart(@PathVariable Long id) {
        cartService.decreaseProductQuantityInCart(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.delete(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }
}
