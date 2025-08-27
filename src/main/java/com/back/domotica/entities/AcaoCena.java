package com.back.domotica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class AcaoCena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAcao;

    @NotBlank(message = "O nome da ação da cena é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    private Integer ordem;

    private Long intervaloSegundos;

    private boolean estadoDesejado;

    @ManyToOne
    @JoinColumn(name = "idCena")
    private Cena cena;

    @ManyToOne
    @JoinColumn(name = "idDispositivo")
    private Dispositivo dispositivo;

    public void executar() {
        if (dispositivo != null) {
            if (estadoDesejado) {
                dispositivo.ligar();
            } else {
                dispositivo.desligar();
            }
        }
    }
}
