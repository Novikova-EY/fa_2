package ru.novikova.market.core.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.novikova.market.api.dtos.CartDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${url.cart}")
    private String cartUrl;

    public Optional<CartDto> getCurrentCart() {
        return Optional.ofNullable(restTemplate.getForObject(cartUrl + "/api/v1/cart/", CartDto.class));
    }

    public void clear() {
        restTemplate.getForObject(cartUrl + "/api/v1/cart/clear", CartDto.class);
    }
}
