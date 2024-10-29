package edu.architect_711.words.model.entity;

import edu.architect_711.words.model.dto.PersonAuthorities;
import edu.architect_711.words.model.dto.PersonRole;
import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;
import org.hibernate.type.SqlTypes;

@NoArgsConstructor
@Data @Entity
@Table(name = "authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Person userId;

    private String api_key; 

    @Column(nullable = false, name = "authorities", columnDefinition = "user_authorities[]")
    @Type(
            value = EnumArrayType.class,
            parameters = @Parameter(
                    name = AbstractArrayType.SQL_ARRAY_TYPE,
                    value = "user_authorities"
            )
    )
    private PersonAuthorities[] authorities;

    @Column(nullable = false, name = "role", columnDefinition = "user_role")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    public Authorities(Person userId, String api_key, PersonAuthorities[] authorities, PersonRole role) {
        this.userId = userId;
        this.api_key = api_key;
        this.authorities = authorities;
        this.role = role;
    }

}
