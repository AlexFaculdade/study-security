package com.exemplo.demo.config;

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Cria um usuário ao iniciar a aplicação
        // Senha '123456' será criptografada antes de salvar
        Usuario user = new Usuario("admin", passwordEncoder.encode("123456"), "ADMIN");
        repository.save(user);
    }
}