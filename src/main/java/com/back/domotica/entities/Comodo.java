package com.back.domotica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comodo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference // << ADICIONADO AQUI
    private List<Dispositivo> dispositivos = new ArrayList<>();
}
