package ru.novikova.spring.market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Long productId;
    private Long orderId;
    private int quantity;
    private int pricePerProduct;
    private int price;
}
