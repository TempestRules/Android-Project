package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Item")
@Table(name = "item")
public class Item {

    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "item_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "item_name",
            nullable = false,
            //TODO: Should item name be updatable?
            updatable = true
    )
    private String name;

    @Column(
            name = "expiration_date",
            nullable = true,
            updatable = true,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime expiration_date;

    @Column(
            name = "unit",
            updatable = true,
            nullable = false
    )
    private String unit;

    public Item() {
    }

    public Item(String name, LocalDateTime expiration_date, String unit) {
        this.name = name;
        this.expiration_date = expiration_date;
        this.unit = unit;
    }
}
