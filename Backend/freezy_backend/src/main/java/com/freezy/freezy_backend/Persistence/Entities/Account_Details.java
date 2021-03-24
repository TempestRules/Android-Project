package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

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

    public Account_Details() {
    }

    public Account_Details(String name) {
        this.name = name;
    }
}
