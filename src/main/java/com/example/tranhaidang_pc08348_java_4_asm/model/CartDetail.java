package com.example.tranhaidang_pc08348_java_4_asm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_details")
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int quantity;
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
