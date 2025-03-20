package com.example.tranhaidang_pc08348_java_4_asm.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int price;
    int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne @JoinColumn(name = "product_id")
    Product product;
}
