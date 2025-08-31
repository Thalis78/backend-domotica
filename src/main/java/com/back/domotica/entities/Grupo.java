package com.back.domotica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @NotBlank(message = "O nome do grupo é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "grupo_dispositivo",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_dispositivo")
    )
    private List<Dispositivo> dispositivos;

    public void ligarTodosDispositivos() {
        if (dispositivos != null) {
            for (Dispositivo dispositivo : dispositivos) {
                dispositivo.ligar();
            }
        }
    }

    public void desligarTodosDispositivos() {
        if (dispositivos != null) {
            for (Dispositivo dispositivo : dispositivos) {
                dispositivo.desligar();
            }
        }
    }


}
