package com.back.domotica.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoricoSimplesDTO {
    private String entidade;
    private String nome;
    private String nomeComodo;
}
