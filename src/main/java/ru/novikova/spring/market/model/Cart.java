package ru.novikova.spring.market.model;

import lombok.Data;
import ru.novikova.spring.market.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data

public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
    }

    public void add(Product product) {
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

    public void delete(Product product) {
        if (items.removeIf(item -> item.getProductId().equals(product.getId()))) {
            recalculate();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    public void increaseProductQuantityInCart(Product product) {
        for (CartItem item : items) {
            if (item.getProductId().equals(product.getId())) {
                item.changeQuantity(1);
                recalculate();
            }
        }
    }

    public void decreaseProductQuantityInCart(Product product) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductId().equals(product.getId())) {
                if (items.get(i).getQuantity() == 1) {
                    delete(product);
                    recalculate();
                    return;
                }
                items.get(i).changeQuantity(-1);
                recalculate();
            }
        }
    }
}
