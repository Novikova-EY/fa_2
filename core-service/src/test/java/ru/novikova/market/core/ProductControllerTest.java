package ru.novikova.market.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.novikova.market.core.entities.Product;
import ru.novikova.market.core.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @WithMockUser(username = "Bob", authorities = "USER")
    public void getAllProductsTest() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Bread");
        product.setPrice(BigDecimal.valueOf(60));
        List<Product> allProducts = new ArrayList<>(Arrays.asList(product));

        given(productRepository.findAll()).willReturn(allProducts);

        mvc
                .perform(
                        get("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(allProducts.get(0).getTitle())));
    }

    // выдает ошибку {"statusCode":404,"message":"Продукт не найден, id: 1"}
    @Test
    @WithMockUser(username = "Bob", authorities = "USER")
    public void getProductByIdTest() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Bread");
        product.setPrice(BigDecimal.valueOf(60));

        given(productRepository.findById(1L)).willReturn(product);

        mvc
                .perform(
                        get("/api/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(product)));
    }
}
