package com.lucianoneves.contatos.controllers;

import com.lucianoneves.contatos.dtos.AutenticacaoDto;
import com.lucianoneves.contatos.dtos.RegistroDto;
import com.lucianoneves.contatos.models.UsuariosModel;
import com.lucianoneves.contatos.repositories.UsuariosRepository;
import com.lucianoneves.contatos.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    final AuthenticationManager authenticationManager;
    final UsuariosRepository usuariosRepository;
    final private TokenService tokenService;

    public AutenticacaoController(
            AuthenticationManager authenticationManager,
            UsuariosRepository usuariosRepository,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuariosRepository = usuariosRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/acesso")
    public ResponseEntity acesso(@RequestBody @Valid AutenticacaoDto autenticacaoDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        autenticacaoDto.username(),
                        autenticacaoDto.password()
                );
        var autentica = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.geraToken((UsuariosModel) autentica.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body("Token: " + token);
    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody @Valid RegistroDto registroDto) {
        if (this.usuariosRepository.findByUsername(registroDto.username()) != null) return
                ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Usuário já existe");
        String criptografiaDeSenha = new BCryptPasswordEncoder().encode(registroDto.password());
        UsuariosModel usuariosModel = new UsuariosModel(
                registroDto.username(),
                criptografiaDeSenha,
                registroDto.role()
        );
        this.usuariosRepository.save(usuariosModel);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário salvo com sucesso.");
    }

}
