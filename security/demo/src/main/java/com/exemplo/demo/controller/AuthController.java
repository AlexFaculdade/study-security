package com.exemplo.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.demo.dto.CadastroDTO;
import com.exemplo.demo.dto.LoginDTO;
import com.exemplo.demo.service.AutenticacaoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AutenticacaoService usuarioService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthController(AuthenticationManager authenticationManager, AutenticacaoService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody CadastroDTO cadastro) {
        // Verifica se já existe (omiti a verificação para simplificar, mas seria ideal ter)
        usuarioService.salvarUsuario(cadastro);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login, HttpServletRequest request, HttpServletResponse response) {
        // 1. Cria o token com os dados que vieram do Frontend
        var token = new UsernamePasswordAuthenticationToken(login.username(), login.password());

        // 2. Pede para o AuthenticationManager verificar se a senha bate
        Authentication authentication = authenticationManager.authenticate(token);

        // 3. Se chegou aqui, a senha está correta! Agora criamos a sessão manualmente.
        // Isso é necessário porque estamos fazendo login via REST e não via formulário HTML padrão.
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        
        // 4. Salva o contexto na sessão (cria o JSESSIONID)
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok("Login realizado com sucesso!");
    }
}