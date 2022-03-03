package ru.novikova.market.cart.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.novikova.market.api.dtos.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${url.core}")
    private String coreUrl;

    public Optional<ProductDto> getProductDtoById(long id) {
        return Optional.ofNullable(restTemplate.getForObject(coreUrl + "/api/v1/products/" + id, ProductDto.class));
    }
}
