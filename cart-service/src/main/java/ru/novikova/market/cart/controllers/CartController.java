package ru.novikova.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.novikova.market.api.dtos.CartDto;
import ru.novikova.market.cart.converters.CartConverter;
import ru.novikova.market.cart.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
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
