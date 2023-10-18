package com.lucianoneves.contatos.dtos;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContatosDto {

    @NotBlank
    @Column(nullable = false)
    private String nomeCompleto;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    @NotBlank
    @Size(max = 11)
    @Column(nullable = false)
    private String telefone;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
