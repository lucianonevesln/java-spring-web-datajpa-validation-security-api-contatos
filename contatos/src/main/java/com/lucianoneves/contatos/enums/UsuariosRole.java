package com.lucianoneves.contatos.enums;

public enum UsuariosRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UsuariosRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
