package edu.architect_711.words.entities.db;

import edu.architect_711.words.entities.JwtToken;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "token")
@NoArgsConstructor
public class JwtTokenEntity implements JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access", nullable = false)
    private String accessToken;

    @Column(name = "refresh", nullable = false)
    private String refreshToken;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    public JwtTokenEntity(String accessToken, String refreshToken,
            boolean isActive, AccountEntity accountEntity) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isActive = isActive;
        this.accountEntity = accountEntity;
    }
}
