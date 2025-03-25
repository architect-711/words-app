package edu.architect_711.words.entities.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.architect_711.words.entities.Account;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor @Builder
@NoArgsConstructor @Data @Entity @Table(name = "account")
public class AccountEntity implements UserDetails, Account { // TODO implement all UserDetails methods as variables, to BAAAN!!! people easier
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // TODO make a table

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL)
    private Set<JwtTokenEntity> jwtTokenEntities;

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL)
    private Set<WordEntity> wordEntities;

    public AccountEntity(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
