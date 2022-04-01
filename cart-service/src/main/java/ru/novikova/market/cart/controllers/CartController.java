package ru.novikova.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.novikova.market.api.dtos.CartDto;
import ru.novikova.market.api.dtos.StringResponse;
import ru.novikova.market.cart.converters.CartConverter;
import ru.novikova.market.cart.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username,
                                  @PathVariable String uuid) {
        String targetUuid = getUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addToCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getUuid(username, uuid);
        cartService.add(targetUuid, id);
    }

    @GetMapping("/{uuid}/increase/{id}")
    public void increaseProductQuantityInCart(@RequestHeader(name = "username", required = false) String username,
                                              @PathVariable String uuid,
                                              @PathVariable Long id) {
        String targetUuid = getUuid(username, uuid);
        cartService.increaseProductQuantityInCart(targetUuid, id);
    }

    @GetMapping("/{uuid}/decrease/{id}")
    public void decreaseProductQuantityInCart(@RequestHeader(name = "username", required = false) String username,
                                              @PathVariable String uuid,
                                              @PathVariable Long id) {
        String targetUuid = getUuid(username, uuid);
        cartService.decreaseProductQuantityInCart(targetUuid, id);
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductFromCart(@RequestHeader(name = "username", required = false) String username,
                                      @PathVariable String uuid,
                                      @PathVariable Long id) {
        String targetUuid = getUuid(username, uuid);
        cartService.delete(targetUuid, id);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String uuid) {
        String targetUuid = getUuid(username, uuid);
        cartService.clear(targetUuid);
    }

    private String getUuid (String username, String uuid) {
        if (username != null) {
            return  username;
        }
        return uuid;
    }
}
