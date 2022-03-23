package ru.novikova.market.cart;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.cart.integration.ProductServiceIntegration;
import ru.novikova.market.cart.services.CartService;

import java.math.BigDecimal;

@SpringBootTest(classes = CartService.class)
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @BeforeEach
    public void initCart() {
        cartService.clear();
    }

    @Test
    public void addTest() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setPrice(BigDecimal.valueOf(150));
        productDto.setTitle("Bread");

        Mockito.doReturn(productDto).when(productServiceIntegration).getProductDtoById(1L);
        cartService.add(1L);
        cartService.add(1L);
        cartService.add(1L);

        Mockito.verify(productServiceIntegration, Mockito.times(3)).getProductDtoById(ArgumentMatchers.eq(1L));
        Assertions.assertEquals(1, cartService.getCurrentCart().getItems().size());
    }
}