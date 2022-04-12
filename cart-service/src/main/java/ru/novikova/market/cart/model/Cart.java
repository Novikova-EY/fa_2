package ru.novikova.market.cart.model;

import lombok.Data;
import ru.novikova.market.api.dtos.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data

public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    public void add(ProductDto product) {
        for (CartItem item : items) {
            if (item.getProductId().equals(product.getId())) {
                item.changeQuantity(1);
                recalculate();
                return;
            }
        }
        items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        recalculate();
    }

    public void delete(Long productDtoId) {
        if (items.removeIf(item -> item.getProductId().equals(productDtoId))) {
            recalculate();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    public void increaseProductQuantityInCart(Long productDtoId) {
        for (CartItem item : items) {
            if (item.getProductId().equals(productDtoId)) {
                item.changeQuantity(1);
                recalculate();
            }
        }
    }

    public void decreaseProductQuantityInCart(Long productDtoId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductId().equals(productDtoId)) {
                if (items.get(i).getQuantity() == 1) {
                    delete(productDtoId);
                    recalculate();
                    return;
                }
                items.get(i).changeQuantity(-1);
                recalculate();
            }
        }
    }
}
