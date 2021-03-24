package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Collection")
@Table(name = "collection")
public class Collection {

    @Id
    @SequenceGenerator(
            name = "collection_sequence",
            sequenceName = "collection_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "collection_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "collection_token",
            nullable = false,
            updatable = false
    )
    private String collection_token;

    //One to many relationship with Account_Details
    @ManyToOne
    @JoinColumn(
            name = "account_details_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "account_details_collection_FK"
            )
    )
    private Account_Details account_details;

    //One to many relationship with Storage_Unit
    @OneToMany(
            mappedBy = "collection",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Storage_Unit> storage_units = new ArrayList<>();

    //One to many relationship with Category
    @OneToMany(
            mappedBy = "collection",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Category> categories = new ArrayList<>();

    public Collection() {
    }

    public Collection(String collection_token) {
        this.collection_token = collection_token;
    }

    //Methods to add or remove storage units. Keeps them both in sync.
    public void addStorage_Unit(Storage_Unit storage_unit) {
        if (!this.storage_units.contains(storage_unit)) {
            this.storage_units.add(storage_unit);
            storage_unit.setCollection(this);
        }
    }
    public void removeStorage_Unit(Storage_Unit storage_unit) {
        if (this.storage_units.contains(storage_unit)) {
            this.storage_units.remove(storage_unit);
            storage_unit.setCollection(null);
        }
    }

    //Methods to add or remove categories. Keeps them both in sync.
    public void addCategory(Category category) {
        if (!this.categories.contains(category)) {
            this.categories.add(category);
            category.setCollection(this);
        }
    }
    public void removeCategory(Category category) {
        if (this.categories.contains(category)) {
            this.categories.remove(category);
            category.setCollection(null);
        }
    }

    //Getters and setters
    public String getCollection_token() {
        return collection_token;
    }

    public void setCollection_token(String collection_token) {
        this.collection_token = collection_token;
    }

    public Account_Details getAccount_details() {
        return account_details;
    }

    public void setAccount_details(Account_Details account_details) {
        this.account_details = account_details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Storage_Unit> getStorage_units() {
        return storage_units;
    }

    public void setStorage_units(List<Storage_Unit> storage_units) {
        this.storage_units = storage_units;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", collection_token=" + collection_token +
                ", account_details=" + account_details +
                ", storage_units=" + storage_units +
                ", categories=" + categories +
                '}';
    }
}
