package ru.novikova.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.cart.integration.ProductServiceIntegration;
import ru.novikova.market.cart.model.Cart;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart)redisTemplate.opsForValue().get(targetUuid);
    }

    public void add(String uuid, Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        execute(uuid, cart -> cart.add(productDto));
    }

    public void delete(String uuid, Long productDtoId) {
        execute(uuid, cart -> cart.delete(productDtoId));
    }

    public void clear(String uuid) {
        execute(uuid, Cart::clear);
    }

    public void increaseProductQuantityInCart(String uuid, Long productDtoId) {
        execute(uuid, cart -> cart.increaseProductQuantityInCart(productDtoId));
    }

    public void decreaseProductQuantityInCart(String uuid, Long productDtoId) {
        execute(uuid, cart -> cart.decreaseProductQuantityInCart(productDtoId));
    }

    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }
}
