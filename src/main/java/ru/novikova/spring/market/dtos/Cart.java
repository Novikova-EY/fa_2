package ru.novikova.spring.market.dtos;

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

    public void add(Product product) {
        boolean checkProductInCart = false;
        if (!(items == null)) {
            for (CartItem p : items) {
                if (p.getProductId().equals(product.getId())) {
                    p.setQuantity(p.getQuantity() + 1);
                    checkProductInCart = true;
                }
            }
        }
        if (!checkProductInCart) {
            items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        }
        recalculate();
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
    }

    public void increaseProductQuantityInCart(Product product) {
        for (CartItem p : items) {
            if (p.getProductId().equals(product.getId())) {
                p.setQuantity(p.getQuantity() + 1);
                recalculate();
            }
        }
    }

    public void decreaseProductQuantityInCart(Product product) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductId().equals(product.getId())) {
                if (items.get(i).getQuantity() == 1) {
                    deleteProductFromCart(product);
                    recalculate();
                    return;
                }
                items.get(i).setQuantity(items.get(i).getQuantity() - 1);
                recalculate();
            }
        }
    }

    public void deleteProductFromCart(Product product) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductId().equals(product.getId())) {
                items.remove(i);
                recalculate();
            }
        }
    }

    public void clear() {
        items.clear();
        recalculate();
    }
}
