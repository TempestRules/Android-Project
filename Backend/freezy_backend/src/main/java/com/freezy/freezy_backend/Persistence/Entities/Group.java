package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Group")
@Table(name = "group")
public class Group {

    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "group_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "group_token",
            nullable = false,
            updatable = false
    )
    private UUID group_token;

    //One to many relationship with Account_Details
    @ManyToOne
    @JoinColumn(
            name = "account_details_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "account_details_group_FK"
            )
    )
    private Account_Details account_details;

    //One to many relationship with Storage_Unit
    @OneToMany(
            mappedBy = "group",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Storage_Unit> storage_units = new ArrayList<>();

    //One to many relationship with Category
    @OneToMany(
            mappedBy = "group",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Category> categories = new ArrayList<>();

    public Group() {
    }

    public Group(UUID group_token) {
        this.group_token = group_token;
    }

    //Methods to add or remove storage units. Keeps them both in sync.
    public void addStorage_Unit(Storage_Unit storage_unit) {
        if (!this.storage_units.contains(storage_unit)) {
            this.storage_units.add(storage_unit);
            storage_unit.setGroup(this);
        }
    }
    public void removeStorage_Unit(Storage_Unit storage_unit) {
        if (this.storage_units.contains(storage_unit)) {
            this.storage_units.remove(storage_unit);
            storage_unit.setGroup(null);
        }
    }

    //Methods to add or remove categories. Keeps them both in sync.
    public void addCategory(Category category) {
        if (!this.categories.contains(category)) {
            this.categories.add(category);
            category.setGroup(this);
        }
    }
    public void removeCategory(Category category) {
        if (this.categories.contains(category)) {
            this.categories.remove(category);
            category.setGroup(null);
        }
    }

    //Getters and setters
    public UUID getGroup_token() {
        return group_token;
    }

    public void setGroup_token(UUID group_token) {
        this.group_token = group_token;
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
        return "Group{" +
                "id=" + id +
                ", group_token=" + group_token +
                ", account_details=" + account_details +
                ", storage_units=" + storage_units +
                ", categories=" + categories +
                '}';
    }
}
