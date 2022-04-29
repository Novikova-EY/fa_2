package ru.novikova.market.api.dtos;

public class AdminDto {
    private String value;

    public AdminDto(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
