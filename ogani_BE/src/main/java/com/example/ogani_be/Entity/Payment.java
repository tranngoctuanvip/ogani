package com.example.ogani_be.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "paymentCode")
    private String paymentCode;

    @Column(name = "money")
    private Integer money;

    @Column(name = "description")
    private String  description;

    @Column(name = "errorCode")
    private String errorCode;

    @Column(name = "transactionCode")
    private String transactionCode;

    @Column(name = "bankCode")
    private String bankCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime paymentTime;

    @Column(name = "transactionStatus")
    private Integer transactionStatus;

    @Column(name = "orderId")
    private Long orderId;
}
