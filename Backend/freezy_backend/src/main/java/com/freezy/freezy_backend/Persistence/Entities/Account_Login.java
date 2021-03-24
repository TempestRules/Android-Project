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


    @OneToOne(mappedBy = "account_login")
    private Token token;

    @OneToOne(mappedBy = "account_login")
    private Account_Details account_details;

    public Account_Login() {
    }

    public Account_Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Account_Details getAccount_details() {
        return account_details;
    }

    public void setAccount_details(Account_Details account_details) {
        this.account_details = account_details;
    }

    @Override
    public String toString() {
        return "Account_Login{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token=" + token +
                ", account_details=" + account_details +
                '}';
    }
}
