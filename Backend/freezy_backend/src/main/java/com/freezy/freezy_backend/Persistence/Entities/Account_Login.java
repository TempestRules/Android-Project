package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Account_Login")
@Table(name = "account_login")
public class Account_Login {

    @Id
    @SequenceGenerator(
            name = "account_login_sequence",
            sequenceName = "account_login_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "account_login_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            updatable = false
    )
    private String password;

    public Account_Login() {
    }

    public Account_Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
