package ru.novikova.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.novikova.market.core.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(long id);

    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, Integer minPrice, Integer maxPrice);
}
