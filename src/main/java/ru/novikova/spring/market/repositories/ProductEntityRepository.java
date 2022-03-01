package ru.novikova.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.novikova.spring.market.entities.ProductEntity;

import java.util.Optional;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select p from ProductEntity p where p.id = ?1")
    Optional<ProductEntity> findById(long id);
}
