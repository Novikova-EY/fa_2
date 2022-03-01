package ru.novikova.spring.market.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.novikova.spring.market.dtos.ProductDto;
import ru.novikova.spring.market.entities.Product;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

    public Product dtoToEntity(ProductDto productDto) {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());
        return p;
    }
}
