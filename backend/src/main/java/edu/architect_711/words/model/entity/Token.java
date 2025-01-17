package edu.architect_711.words.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Table(name = "tokens")
@NoArgsConstructor
public class Token {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "is_logged_out", nullable = false)
    private boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Token(String accessToken, String refreshToken, boolean isLoggedOut, Person person) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isLoggedOut = isLoggedOut;
        this.person = person;
    }
}
