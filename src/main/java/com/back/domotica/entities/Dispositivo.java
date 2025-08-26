package com.back.domotica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private boolean estado;

    @NotNull(message = "O cômodo do dispositivo é obrigatório")
    @ManyToOne
    @JoinColumn(name = "idComodo", nullable = false)
    private Comodo comodo;
}
