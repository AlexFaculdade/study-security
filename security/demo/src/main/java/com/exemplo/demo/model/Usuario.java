package com.exemplo.demo.model;

import jakarta.persistence.*;

@Entity // Diz ao JPA que isso vira uma tabela no banco
@Table(name = "users") // Nome da tabela
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // O username não pode repetir
    private String username;
    private String password;
    private String role; // Ex: "ADMIN", "USER"

    // Construtor vazio (obrigatório para o JPA)
    public Usuario() {}

    // Construtor para facilitar nossa vida no teste
    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters e Setters (Essenciais para o Spring ler os dados)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}