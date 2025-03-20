package com.example.tranhaidang_pc08348_java_4_asm.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "logers")
public class Loger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String uri;
    Date time;
    @ManyToOne @JoinColumn(name = "account_id")
    Account account;
}
