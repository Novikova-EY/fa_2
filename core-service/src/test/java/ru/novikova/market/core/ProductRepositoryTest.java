package ru.novikova.market.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.novikova.market.core.entities.Order;
import ru.novikova.market.core.entities.Product;
import ru.novikova.market.core.repositories.ProductRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void orderRepositoryTest() {
        Product product = new Product();
        product.setTitle("Bread");

        entityManager.persist(product);
        entityManager.flush();

        List<Product> productList = productRepository.findAll();

        Assertions.assertEquals(4, productList.size());
        Assertions.assertEquals("Bread", productList.get(1).getTitle());
    }

    @Test
    public void initDbTest() {
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(3, productList.size());
    }
}
