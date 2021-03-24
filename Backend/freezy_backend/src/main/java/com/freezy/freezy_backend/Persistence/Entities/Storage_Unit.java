package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

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

    public Storage_Unit() {
    }

    public Storage_Unit(String name) {
        this.name = name;
    }
}
