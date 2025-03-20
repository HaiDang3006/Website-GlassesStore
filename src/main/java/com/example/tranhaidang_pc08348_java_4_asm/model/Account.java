package com.example.tranhaidang_pc08348_java_4_asm.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String fullname;
    String email;
    String password;
    String photo;
    Boolean activated;
    boolean admin;


    @OneToMany(mappedBy = "account")
    List<Loger> logers;

    @OneToMany(mappedBy = "account")
    List<CartDetail>cartDetails;

    @OneToMany(mappedBy = "account")
    List<Favorite> favorites;

    @OneToMany(mappedBy = "account")
    List<Order>orders;

}
