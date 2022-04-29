package ru.novikova.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.core.entities.Product;
import ru.novikova.market.core.repositories.ProductRepository;
import ru.novikova.market.core.repositories.specifications.ProductsSpecifications;
import ru.novikova.market.core.validators.ProductValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public Page<Product> findAll(Specification<Product> spec, Integer page) {
        return productRepository.findAll(spec, PageRequest.of(page, 10));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Specification<Product> createSpecByFilters(Integer minPrice, Integer maxPrice, String title) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (title != null) {
            spec = spec.and(ProductsSpecifications.titleLike(title));
        }
        return spec;
    }

    public Product  createNewProduct(ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        productRepository.save(product);
        return product;
    }
}
