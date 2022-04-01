package ru.novikova.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.cart.integration.ProductServiceIntegration;
import ru.novikova.market.cart.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> tempCart;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    @PostConstruct
    public void init() {
        tempCart = new HashMap<>();
    }

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!tempCart.containsKey(targetUuid)) {
            tempCart.put(targetUuid, new Cart());
        }
        return tempCart.get(targetUuid);
    }

    public void add(String uuid, Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        getCurrentCart(uuid).add(productDto);
    }

    public void delete(String uuid, Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        getCurrentCart(uuid).delete(productDto);
    }

    public void clear(String uuid) {
        getCurrentCart(uuid).clear();
    }

    public void increaseProductQuantityInCart(String uuid, Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        getCurrentCart(uuid).increaseProductQuantityInCart(productDto);
    }

    public void decreaseProductQuantityInCart(String uuid, Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        getCurrentCart(uuid).decreaseProductQuantityInCart(productDto);
    }
}
