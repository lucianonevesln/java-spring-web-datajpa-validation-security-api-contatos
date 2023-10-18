package com.lucianoneves.contatos.repositories;

import com.lucianoneves.contatos.models.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<UsuariosModel, Integer> {
    UserDetails findByUsername(String username);
}
