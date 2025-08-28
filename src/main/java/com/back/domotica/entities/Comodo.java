package com.back.domotica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComodo;

    @NotBlank(message = "O nome do cômodo é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;
}
