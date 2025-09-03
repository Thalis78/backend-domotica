package com.back.domotica.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDispositivo;

    @NotBlank(message = "O nome do dispositivo é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comodo_id")
    @JsonBackReference
    private Comodo comodo;

    public void ligar() {
        this.estado = true;
    }

    public void desligar() {
        this.estado = false;
    }
}
