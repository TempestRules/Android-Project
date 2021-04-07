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


    @OneToOne(
            mappedBy = "account_login",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Token token;

    @OneToOne(
            mappedBy = "account_login",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Account_Details account_details;

    public Account_Login() {
    }

    public Account_Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addToken(Token token) {
        if (this.token == null) {
            this.token = token;
            token.setAccount_login(this);
        }
    }
    public void removeToken(Token token) {
        if (this.token != null) {
            this.token = null;
            token.setAccount_login(null);
        }
    }

    public void addAccount_Details(Account_Details account_details) {
        if (this.account_details == null) {
            this.account_details = account_details;
            account_details.setAccount_login(this);
        }
    }
    public void removeAccount_Details(Account_Details account_details) {
        if (this.account_details != null) {
            this.account_details = null;
            account_details.setAccount_login(null);
        }
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
