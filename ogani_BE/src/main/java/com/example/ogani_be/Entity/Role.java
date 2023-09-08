package com.example.ogani_be.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nameRole")
    private String nameRole;
    @Column(name = "code")
    private String code;

    public Role(String name, String code) {
        this.nameRole= name;
        this.code = code;
    }

    public Role() {

    }
}
