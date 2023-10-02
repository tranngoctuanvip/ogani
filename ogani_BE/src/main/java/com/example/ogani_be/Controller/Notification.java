package com.example.ogani_be.Controller;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "notification")
    private String notification;
}
