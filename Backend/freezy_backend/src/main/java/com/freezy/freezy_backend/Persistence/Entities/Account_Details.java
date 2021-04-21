package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Account_Details")
@Table(name = "account_details")
public class Account_Details {

    @Id
    @SequenceGenerator(
            name = "account_details_sequence",
            sequenceName = "account_details_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "account_details_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            updatable = false
    )
    private String name;

    //One to one relationship with Account_Login
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "account_login_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "Account_Details_Account_Login_FK"
            )
    )
    private Account_Login account_login;

    //One to many relationship with collection
    @OneToMany(
            mappedBy = "account_details",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Collection> collections = new ArrayList<>();

    public Account_Details() {
    }

    public Account_Details(String name) {
        this.name = name;
    }

    //Methods to add or remove collections. Keeps them both in sync.
    public void addCollection(Collection collection) {
        if (!this.collections.contains(collection)) {
            this.collections.add(collection);
            collection.setAccount_details(this);
        }
    }
    public void removeCollection(Collection collection) {
        if (this.collections.contains(collection)) {
            this.collections.remove(collection);
            collection.setAccount_details(null);
        }
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account_Login getAccount_login() {
        return account_login;
    }

    public void setAccount_login(Account_Login account_login) {
        this.account_login = account_login;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    @Override
    public String toString() {
        return "Account_Details{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account_login=" + account_login +
                ", collections=" + collections +
                '}';
    }
}
