package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Token")
@Table(name = "token")
public class Token {

    @Id
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "token_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "token",
            nullable = false,
            updatable = false
    )
    private UUID token;

    @Column(
            name = "date",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime date;

    public Token() {
    }

    public Token(UUID token, LocalDateTime date) {
        this.token = token;
        this.date = date;
    }
}
