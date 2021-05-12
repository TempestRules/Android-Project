package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(
            name = "quantity",
            updatable = true,
            nullable = false
    )
    private Double quantity;

    //One to many relationship with Storage_Unit
    @ManyToOne
    @JoinColumn(
            name = "storage_unit_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "storage_unit_item_FK"
            )
    )
    private Storage_Unit storage_unit;

    //Many to many relationship with Category
    @ManyToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinTable(
            name = "Item_Category",
            joinColumns = @JoinColumn(
                    name = "item_id",
                    foreignKey = @ForeignKey(name = "join_item_to_category_FK")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    foreignKey = @ForeignKey(name = "join_category_to_item_FK")
            )
    )
    private List<Category> categories = new ArrayList<>();

    public Item() {
    }

    public Item(String name, LocalDateTime expiration_date, String unit, double quantity) {
        this.name = name;
        this.expiration_date = expiration_date;
        this.unit = unit;
        this.quantity = quantity;
    }

    //Keeps items and categories synced both ways.
    public void addCategoryToItem(Category category) {
        categories.add(category);
        category.getItems().add(this);
    }
    public void removeCategoryFromItem(Category category) {
        categories.remove(category);
        category.getItems().remove(this);
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Storage_Unit getStorage_unit() {
        return storage_unit;
    }

    public void setStorage_unit(Storage_Unit storage_unit) {
        this.storage_unit = storage_unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expiration_date=" + expiration_date +
                ", unit='" + unit + '\'' +
                ", storage_unit=" + storage_unit +
                ", categories=" + categories +
                '}';
    }
}
