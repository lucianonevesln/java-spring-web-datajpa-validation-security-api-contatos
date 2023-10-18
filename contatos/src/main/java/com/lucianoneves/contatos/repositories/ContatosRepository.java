package com.lucianoneves.contatos.repositories;

import com.lucianoneves.contatos.models.ContatosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatosRepository extends JpaRepository<ContatosModel, Integer> {
    boolean existsByTelefone(String telefone);
}
