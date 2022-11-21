package com.example.firma.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ishchi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomi;
    private String telRaqam;
    @OneToOne
    private Manzil manzil;
    @OneToOne
    private Bolim bolim;
}
