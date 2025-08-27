package com.back.domotica.controllers;

import com.back.domotica.dtos.HistoricoSimplesDTO;
import com.back.domotica.entities.*;
import com.back.domotica.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private ComodoRepository comodoRepository;

    @Autowired
    private CenaRepository cenaRepository;

    @Autowired
    private AcaoCenaRepository acaoCenaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @GetMapping
    public ResponseEntity<Map<String, List<HistoricoSimplesDTO>>> listarHistoricoCompleto() {

        List<HistoricoSimplesDTO> comodos = comodoRepository.findAll()
                .stream()
                .map(c -> new HistoricoSimplesDTO("Comodo", c.getNome(), null))
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> cenas = cenaRepository.findAll()
                .stream()
                .map(c -> new HistoricoSimplesDTO("Cena", c.getNome(), null))
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> acoesCena = acaoCenaRepository.findAll()
                .stream()
                .map(a -> {
                    String nomeComodo = a.getDispositivo() != null && a.getDispositivo().getComodo() != null
                            ? a.getDispositivo().getComodo().getNome()
                            : null;
                    return new HistoricoSimplesDTO("AcaoCena", a.getNome(), nomeComodo);
                })
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> grupos = grupoRepository.findAll()
                .stream()
                .map(g -> new HistoricoSimplesDTO("Grupo", g.getNome(), null))
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> dispositivos = dispositivoRepository.findAll()
                .stream()
                .map(d -> new HistoricoSimplesDTO("Dispositivo", d.getNome(),
                        d.getComodo() != null ? d.getComodo().getNome() : null))
                .collect(Collectors.toList());

        Map<String, List<HistoricoSimplesDTO>> response = new HashMap<>();
        response.put("comodos", comodos);
        response.put("cenas", cenas);
        response.put("acoesCena", acoesCena);
        response.put("grupos", grupos);
        response.put("dispositivos", dispositivos);

        return ResponseEntity.ok(response);
    }
}
