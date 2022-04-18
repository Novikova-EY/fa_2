package ru.novikova.market.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novikova.market.auth.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
