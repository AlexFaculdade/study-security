package com.exemplo.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exemplo.demo.dto.CadastroDTO;
import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Busca o usuario no banco
        var usuario = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // 2. Retorna um objeto User do Spring Security com os dados do nosso banco
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // A senha já deve estar criptografada no banco
                .roles(usuario.getRole())
                .build();
    }

    // ... manter o restante da classe igual ...
    
    // Precisamos injetar o PasswordEncoder aqui também
    

    // Método novo para registrar usuário
    public void salvarUsuario(CadastroDTO dados) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(dados.username());
        // CRUCIAL: Criptografar antes de salvar!
        novoUsuario.setPassword(passwordEncoder.encode(dados.password())); 
        novoUsuario.setRole("USER"); // Todo mundo entra como USER por padrão
        
        repository.save(novoUsuario);
    }
}