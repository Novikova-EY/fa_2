package ru.novikova.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.api.exceptions.ResourceNotFoundException;
import ru.novikova.market.cart.integration.ProductServiceIntegration;
import ru.novikova.market.cart.model.Cart;


import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void add(Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        tempCart.add(productDto);
    }

    public void delete(Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        tempCart.delete(productDto);
    }

    public void clear() {
        tempCart.clear();
    }

    public void increaseProductQuantityInCart(Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        tempCart.increaseProductQuantityInCart(productDto);
    }

    public void decreaseProductQuantityInCart(Long productDtoId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productDtoId);
        tempCart.decreaseProductQuantityInCart(productDto);
    }
}
