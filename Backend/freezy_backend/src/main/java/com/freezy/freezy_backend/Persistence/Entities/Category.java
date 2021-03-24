package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Category")
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "category_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "category_name",
            updatable = true,
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "color",
            updatable = true,
            nullable = false
    )
    private String color;

    public Category() {
    }

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
