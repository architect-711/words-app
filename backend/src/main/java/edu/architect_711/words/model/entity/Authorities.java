package edu.architect_711.words.model.entity;

import edu.architect_711.words.model.dto.PersonAuthorities;
import edu.architect_711.words.model.dto.PersonRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data @Entity @Table(name = "authorities")
public class Authorities {
    @Id @GeneratedValue
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "api_key", nullable = false)
    private String api_key;
    @Column(nullable = false)
    private PersonAuthorities[] authorities;
    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private PersonRole role;

    public Authorities(Long userId, String api_key, PersonAuthorities[] authorities, PersonRole role) {
        this.userId = userId;
        this.api_key = api_key;
        this.authorities = authorities;
        this.role = role;
    }

}
