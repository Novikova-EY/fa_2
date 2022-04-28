package ru.novikova.market.core.validators;

import org.springframework.stereotype.Component;
import ru.novikova.market.api.dtos.ProductDto;
import ru.novikova.market.core.exceptions.ValidationException;
import ru.novikova.market.core.exceptions.ValidationFieldError;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator implements Validator<ProductDto>{

    @Override
    public void validate(ProductDto productDto) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (productDto.getPrice().doubleValue() < 0.01) {
            errors.add(new ValidationFieldError("price", productDto.getPrice().toString(), "Цена продукта не может быть меньше 1 коп."));
        }
        if (productDto.getTitle().length() < 3 || productDto.getTitle().length() > 255) {
            errors.add(new ValidationFieldError("title", productDto.getTitle(), "Длина названия продукта должна находиться в пределах 3-255 символов"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Продукт не прошел проверку", errors);
        }
    }
}
