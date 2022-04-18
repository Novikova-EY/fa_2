package ru.novikova.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.novikova.market.api.dtos.OrderDto;
import ru.novikova.market.api.dtos.OrderPageDto;
import ru.novikova.market.core.converters.OrderConverter;
import ru.novikova.market.core.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username) {
        orderService.createOrder(username);
    }

    @Operation(
            summary = "Запрос на отображение заказов кокретного пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderPageDto.class))
                    ),
            }
    )
    @GetMapping
    public OrderPageDto<OrderDto> getUserOrders(
            @Parameter(description = "Имя текущего пользователя", required = true)
            @RequestHeader String username,

            @Parameter(description = "Номер страницы", required = true)
            @RequestParam(defaultValue = "1", name = "p") Integer page
    ) {
        Page<OrderDto> jpaPageOrder = orderService.findByUsername(username, page - 1).map(orderConverter::entityToDto);
        OrderPageDto<OrderDto> orderPageDto = new OrderPageDto<>();
        orderPageDto.setItems(jpaPageOrder.getContent());
        orderPageDto.setPage(jpaPageOrder.getNumber());
        orderPageDto.setTotalPages(jpaPageOrder.getTotalPages());
        return  orderPageDto;
    }
}
