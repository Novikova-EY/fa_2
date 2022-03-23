package ru.novikova.market.core;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.novikova.market.api.dtos.CartDto;
import ru.novikova.market.api.dtos.CartItemDto;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.core.entities.Product;
import ru.novikova.market.core.integration.CartServiceIntegration;
import ru.novikova.market.core.repositories.OrderRepository;
import ru.novikova.market.core.services.OrderService;
import ru.novikova.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = OrderService.class)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @Test
    public void createOrderTest() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPrice(BigDecimal.valueOf(100));
        cartItemDto.setPricePerProduct(BigDecimal.valueOf(50));
        cartItemDto.setProductId(2564L);
        cartItemDto.setProductTitle("Bread");
        cartItemDto.setQuantity(2);

        CartDto cartDto = new CartDto();
        cartDto.setItems(List.of(cartItemDto));
        cartDto.setTotalPrice(BigDecimal.valueOf(150));

        Mockito.doReturn(cartDto).when(cartServiceIntegration).getCurrentCart();

        Product product = new Product();
        product.setId(2564L);
        product.setPrice(BigDecimal.valueOf(50));
        product.setTitle("Bread");

        Mockito.doReturn(Optional.of(product)).when(productService).findById(2564L);

        orderService.createOrder("Sten");

        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());
        Mockito.verify(cartServiceIntegration).clear();
    }
}