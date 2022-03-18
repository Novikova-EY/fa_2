package ru.novikova.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novikova.market.core.entities.Product;
import ru.novikova.market.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll(Optional<String> title,
                                 Integer minPrice,
                                 Integer maxPrice) {
        if (title.isPresent() && minPrice == null && maxPrice == null) {
            return productRepository.findAll();
        } else {
            return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(title.get(), minPrice, maxPrice);
        }
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
