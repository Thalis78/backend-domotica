package com.back.domotica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Cena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCena;

    @NotBlank(message = "O nome da cena é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    private boolean ativa = true;

    public void ligar() {
        this.ativa = true;
    }

    public void desligar() {
        this.ativa = false;
    }
}
