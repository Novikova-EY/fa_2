package ru.novikova.market.api.dtos;

public class RoleDto {
    private String role;

    public RoleDto(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
