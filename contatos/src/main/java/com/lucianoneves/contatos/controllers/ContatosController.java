package com.lucianoneves.contatos.controllers;

import com.lucianoneves.contatos.dtos.ContatosDto;
import com.lucianoneves.contatos.models.ContatosModel;
import com.lucianoneves.contatos.services.ContatosService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
public class ContatosController {

    final ContatosService contatosService;

    public ContatosController(ContatosService contatosService) {
        this.contatosService = contatosService;
    }

    @PostMapping
    public ResponseEntity<Object> cadastraContato(@RequestBody @Valid ContatosDto contatosDto) {
        if (contatosService.retornaSeExisteTelefone(contatosDto.getTelefone())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Telefone já existe.");
        }
        ContatosModel contatosModel = new ContatosModel();
        BeanUtils.copyProperties(contatosDto, contatosModel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contatosService.salvaContato(contatosModel));
    }

    @GetMapping
    public ResponseEntity<List<ContatosModel>> mostraTodosOsContatos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contatosService.retornaTodosOsContatos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> mostraUmContato(@PathVariable(value = "id") Integer id) {
        Optional<ContatosModel> optionalContatosModel = contatosService.retornaUmContato(id);
        if (optionalContatosModel.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(optionalContatosModel.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Contato não encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizaContato(@PathVariable(value = "id") Integer id,
                                           @RequestBody @Valid ContatosDto contatosDto) {
        Optional<ContatosModel> optionalContatosModel = contatosService.retornaUmContato(id);
        if (optionalContatosModel.isPresent()) {
            ContatosModel contatosModel = new ContatosModel();
            BeanUtils.copyProperties(contatosDto, contatosModel);
            contatosModel.setId(optionalContatosModel.get().getId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(contatosService.salvaContato(contatosModel));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Contato não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluiUmContato(@PathVariable(value = "id") Integer id) {
        Optional<ContatosModel> optionalContatosModel = contatosService.retornaUmContato(id);
        if (optionalContatosModel.isPresent()) {
            contatosService.excluiContato(optionalContatosModel.get());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Contato exluído com sucesso.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Contato não encontrado");
        }
    }

}
