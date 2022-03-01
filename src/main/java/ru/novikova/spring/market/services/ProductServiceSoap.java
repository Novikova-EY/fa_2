package ru.novikova.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novikova.spring.market.entities.ProductEntity;
import ru.novikova.spring.market.repositories.ProductEntityRepository;
import ru.novikova.spring.market.repositories.ProductRepository;
import ru.novikova.spring.market.soap.products.Product;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceSoap {
    private final ProductEntityRepository productEntityRepository;

    public static final Function<ProductEntity, Product> functionEntityToSoap = se -> {
        Product product = new Product();
        product.setId(se.getId());
        product.setTitle(se.getTitle());
        product.setPrice(se.getPrice());
        return product;
    };

    public List<Product> getAllProducts() {
        return productEntityRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public Product getById(long id) {
        return productEntityRepository.findById(id).map(functionEntityToSoap).get();
    }
}
