package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

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

    //One to one relationship with Account_Login
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "account_login_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "Token_Account_Login_FK"
            )
    )
    private Account_Login account_login;

    public Token() {
    }

    public Token(UUID token) {
        this.token = token;
    }

    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Account_Login getAccount_login() {
        return account_login;
    }

    public void setAccount_login(Account_Login account_login) {
        this.account_login = account_login;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token=" + token +
                ", account_login=" + account_login +
                '}';
    }
}
