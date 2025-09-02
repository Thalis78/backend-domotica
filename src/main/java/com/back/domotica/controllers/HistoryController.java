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

    private final CenaRepository cenaRepository;
    private final AcaoCenaRepository acaoCenaRepository;
    private final GrupoRepository grupoRepository;
    private final DispositivoRepository dispositivoRepository;

    public HistoryController(
            CenaRepository cenaRepository,
            AcaoCenaRepository acaoCenaRepository,
            GrupoRepository grupoRepository,
            DispositivoRepository dispositivoRepository) {

        this.cenaRepository = cenaRepository;
        this.acaoCenaRepository = acaoCenaRepository;
        this.grupoRepository = grupoRepository;
        this.dispositivoRepository = dispositivoRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<HistoricoSimplesDTO>>> listarHistoricoCompleto() {

        List<HistoricoSimplesDTO> cenas = cenaRepository.findAll()
                .stream()
                .map(c -> new HistoricoSimplesDTO("Cena", c.getNome()))
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> acoesCena = acaoCenaRepository.findAll()
                .stream()
                .map(a -> new HistoricoSimplesDTO("AcaoCena", a.getNome()))
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> grupos = grupoRepository.findAll()
                .stream()
                .map(g -> new HistoricoSimplesDTO("Grupo", g.getNome()))
                .collect(Collectors.toList());

        List<HistoricoSimplesDTO> dispositivos = dispositivoRepository.findAll()
                .stream()
                .map(d -> new HistoricoSimplesDTO("Dispositivo", d.getNome()))
                .collect(Collectors.toList());

        Map<String, List<HistoricoSimplesDTO>> response = new HashMap<>();
        response.put("cenas", cenas);
        response.put("acoesCena", acoesCena);
        response.put("grupos", grupos);
        response.put("dispositivos", dispositivos);

        return ResponseEntity.ok(response);
    }


}
