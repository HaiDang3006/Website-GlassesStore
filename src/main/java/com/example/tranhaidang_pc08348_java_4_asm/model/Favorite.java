package com.example.tranhaidang_pc08348_java_4_asm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorites")

public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "like_date")
    @Temporal(TemporalType.DATE)
    Date createDate;

    @ManyToOne @JoinColumn(name = "account_id")
    Account account;
    @ManyToOne @JoinColumn(name = "product_id")
    Product product;

}
