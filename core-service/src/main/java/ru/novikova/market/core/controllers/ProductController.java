package ru.novikova.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.novikova.market.api.dtos.ProductPageDto;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.api.exceptions.AppError;
import ru.novikova.market.api.exceptions.ResourceNotFoundException;
import ru.novikova.market.core.converters.ProductConverter;
import ru.novikova.market.core.entities.Product;
import ru.novikova.market.core.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductPageDto.class))
                    )
            }
    )
    @GetMapping
    public ProductPageDto<ProductDto> findProducts(
            @Parameter(description = "Минимальная цена продукта", required = false)
            @RequestParam(required = false, name = "min_price") Integer minPrice,

            @Parameter(description = "Максимальная цена продукта", required = false)
            @RequestParam(required = false, name = "max_price") Integer maxPrice,

            @Parameter(description = "Наименование (часть насименования) продукта", required = false)
            @RequestParam(required = false, name = "title") String title,

            @Parameter(description = "Номер страницы", required = false)
            @RequestParam(defaultValue = "1", name = "p") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        Specification<Product> spec = productService.createSpecByFilters(minPrice, maxPrice, title);
        Page<ProductDto> jpaPage = productService.findAll(spec, page - 1).map(productConverter::entityToDto);

        ProductPageDto<ProductDto> out = new ProductPageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setItems(jpaPage.getContent());
        out.setTotalPages(jpaPage.getTotalPages());
        return out;
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto findProductById(
            @Parameter(description = "Id продукта", required = true)
            @PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
        return productConverter.entityToDto(product);
    }


    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct(
            @Parameter(description = "Создаваемый продукт", required = true)
            @RequestBody ProductDto productDto
    ) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);
    }

    @Operation(
            summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно удален", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProductById(
            @Parameter(description = "Id продукта", required = true)
            @PathVariable Long id) {
        productService.deleteById(id);
    }
}
