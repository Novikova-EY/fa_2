package ru.novikova.market.core.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.novikova.market.api.dtos.AdminDto;
import ru.novikova.market.api.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/check")
    public AdminDto checkRole(
            @Parameter(description = "Роль пользователя из header", required = true)
            @RequestHeader String roles) {
        if (roles.equals("[ROLE_ADMIN]")) {
            return new AdminDto("admin");
        } else throw new ResourceNotFoundException("Доступ запрещен");
    }
}
