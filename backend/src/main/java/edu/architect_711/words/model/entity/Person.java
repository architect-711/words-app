package edu.architect_711.words.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data @Entity @Table(name = "users")
public class Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 16)
    private String username;
    @Column(nullable = false, length = 128)
    private String password;

    public Person(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
