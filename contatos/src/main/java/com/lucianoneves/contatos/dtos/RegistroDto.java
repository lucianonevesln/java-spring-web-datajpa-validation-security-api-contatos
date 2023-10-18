package com.lucianoneves.contatos.dtos;

import com.lucianoneves.contatos.enums.UsuariosRole;

public record RegistroDto(String username, String password, UsuariosRole role) {
}
