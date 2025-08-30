package com.back.domotica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "acao_cena_dispositivos",
            joinColumns = @JoinColumn(name = "idAcao"),
            inverseJoinColumns = @JoinColumn(name = "idDispositivo")
    )
    private List<Dispositivo> dispositivos;

    @ManyToMany
    @JoinTable(
            name = "acao_cena_grupos",
            joinColumns = @JoinColumn(name = "idAcao"),
            inverseJoinColumns = @JoinColumn(name = "idGrupo")
    )
    private List<Grupo> grupos;

    public void executar() {
        if (dispositivos != null) {
            for (Dispositivo dispositivo : dispositivos) {
                if (estadoDesejado) {
                    dispositivo.ligar();
                } else {
                    dispositivo.desligar();
                }
            }
        }

        if (grupos != null) {
            for (Grupo grupo : grupos) {
                if (grupo.getDispositivos() != null) {
                    for (Dispositivo dispositivo : grupo.getDispositivos()) {
                        if (estadoDesejado) {
                            dispositivo.ligar();
                        } else {
                            dispositivo.desligar();
                        }
                    }
                }
            }
        }
    }

}
