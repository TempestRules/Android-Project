package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Storage_Unit")
@Table(name = "storage_unit")
public class Storage_Unit {

    @Id
    @SequenceGenerator(
            name = "storage_unit_sequence",
            sequenceName = "storage_unit_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "storage_unit_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "storage_unit_name",
            nullable = false,
            updatable = true
    )
    private String name;

    @Column(
            name = "storage_unit_color",
            nullable = false,
            updatable = true
    )
    private String color;

    //One to many relationship with Collection
    @ManyToOne
    @JoinColumn(
            name = "storage_unit_collection_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "collection_storage_unit_FK"
            )
    )
    private Collection collection;

    //One to many relationship with Item
    @OneToMany(
            mappedBy = "storage_unit",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Item> items = new ArrayList<>();

    public Storage_Unit() {
    }

    public Storage_Unit(String name, String color) {
        this.name = name;
        this.color = color;
    }

    //Methods to add or remove items. Keeps them both in sync.
    public void addItem(Item item) {
        if (!this.items.contains(item)) {
            this.items.add(item);
            item.setStorage_unit(this);
        }
    }
    public void removeItem(Item item) {
        if (this.items.contains(item)) {
            this.items.remove(item);
            item.setStorage_unit(null);
        }
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String s = "Storage_Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", collection=" + collection.getId();
        for (Item item: items) {
            s += item.getId() + " " + item.getName() + " " + item.getUnit() + " " + item.getExpiration_date();
        }
        return s;
    }
}
