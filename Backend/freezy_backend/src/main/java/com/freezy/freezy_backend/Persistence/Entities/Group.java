package com.freezy.freezy_backend.Persistence.Entities;

import javax.persistence.*;

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

    public Group() {
    }

    public Group(UUID group_token) {
        this.group_token = group_token;
    }
}
