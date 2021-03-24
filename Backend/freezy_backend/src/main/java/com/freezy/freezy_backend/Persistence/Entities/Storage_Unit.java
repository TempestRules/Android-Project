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

    //One to many relationship with Group
    @ManyToOne
    @JoinColumn(
            name = "storage_unit_group_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "group_storage_unit_FK"
            )
    )
    private Group group;

    //One to many relationship with Item
    @OneToMany(
            mappedBy = "storage_unit",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Item> items = new ArrayList<>();

    public Storage_Unit() {
    }

    public Storage_Unit(String name) {
        this.name = name;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    @Override
    public String toString() {
        return "Storage_Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group=" + group +
                ", items=" + items +
                '}';
    }
}
