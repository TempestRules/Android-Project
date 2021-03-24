package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    //One to many relationship with Collection
    @ManyToOne
    @JoinColumn(
            name = "category_collection_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "collection_category_FK"
            )
    )
    private Collection collection;

    //Many to many relationship with Item
    @ManyToMany(
            mappedBy = "categories"
    )
    private List<Item> items = new ArrayList<>();

    public Category() {
    }

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", collection=" + collection +
                ", items=" + items +
                '}';
    }
}
