package com.lucianoneves.contatos.services;

import com.lucianoneves.contatos.models.ContatosModel;
import com.lucianoneves.contatos.repositories.ContatosRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContatosService {

    final ContatosRepository contatosRepository;

    public ContatosService(ContatosRepository contatosRepository) {
        this.contatosRepository = contatosRepository;
    }

    @Transactional
    public ContatosModel salvaContato(ContatosModel contatosModel) {
        return contatosRepository.save(contatosModel);
    }

    public List<ContatosModel> retornaTodosOsContatos() {
        return contatosRepository.findAll();
    }

    public Optional<ContatosModel> retornaUmContato(Integer id) {
        return contatosRepository.findById(id);
    }

    public boolean retornaSeExisteTelefone(String telefone) {
        return contatosRepository.existsByTelefone(telefone);
    }

    public void excluiContato(ContatosModel contatosModel) {
        contatosRepository.delete(contatosModel);
    }

}
