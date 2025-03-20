package com.example.tranhaidang_pc08348_java_4_asm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String image;
    int price;
    @Temporal(TemporalType.DATE)
    Date created_date;
    Boolean available;

    @ManyToOne @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product")
    List<Favorite>favorites;

    @OneToMany(mappedBy = "product")
    List<CartDetail>cartDetails;

    @OneToMany(mappedBy = "product")
    List<OrderDetail>orderDetails;
}
