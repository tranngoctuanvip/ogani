package com.example.ogani_be.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "productId")
    private Long productId;
    @Column(name = "quality")
    private Integer quality;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updateAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime deleteAt;
    @Column(name = "createBy")
    private Long createBy;
    @Column(name = "updateBy")
    private Long updateBy;
    @Column(name = "deleted")
    private Integer deleted;
    @Column(name = "status")
    private Integer status;
}
