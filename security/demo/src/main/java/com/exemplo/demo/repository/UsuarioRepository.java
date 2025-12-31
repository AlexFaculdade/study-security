package com.exemplo.demo.repository;

import com.exemplo.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring cria o SQL automaticamente baseado no nome do método:
    // SELECT * FROM users WHERE username = ?
    Optional<Usuario> findByUsername(String username);
}