package com.back.domotica.controllers;

import com.back.domotica.dtos.DispositivoDTO;
import com.back.domotica.entities.Comodo;
import com.back.domotica.entities.Dispositivo;
import com.back.domotica.services.ComodoService;
import com.back.domotica.services.DispositivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;
    private final ComodoService comodoService;

    public DispositivoController(DispositivoService dispositivoService, ComodoService comodoService) {
        this.dispositivoService = dispositivoService;
        this.comodoService = comodoService;
    }

    @GetMapping
    public ResponseEntity<List<Dispositivo>> listarTodos() {
        List<Dispositivo> dispositivos = dispositivoService.listarTodos();
        return ResponseEntity.ok(dispositivos);
    }

    @PostMapping
    public ResponseEntity<Dispositivo> criar(@Valid @RequestBody DispositivoDTO dispositivoDTO) {
        Comodo comodo = comodoService.buscarPorId(dispositivoDTO.getIdComodo());
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNome(dispositivoDTO.getNome());
        dispositivo.setEstado(dispositivoDTO.getEstado());
        dispositivo.setComodo(comodo);
        Dispositivo salvo = dispositivoService.salvar(dispositivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> atualizar(@PathVariable Long id, @Valid @RequestBody DispositivoDTO dto) {
        Comodo comodo = comodoService.buscarPorId(dto.getIdComodo());
        Dispositivo existente = dispositivoService.buscarPorId(id);
        existente.setNome(dto.getNome());
        existente.setEstado(dto.getEstado());
        existente.setComodo(comodo);
        Dispositivo atualizado = dispositivoService.salvar(existente);
        return ResponseEntity.ok(atualizado);
    }

    @PutMapping("/{id}/ligar")
    public ResponseEntity<?> ligarDispositivo(@PathVariable Long id) {
        Dispositivo dispositivo = dispositivoService.buscarPorId(id);

        if (Boolean.TRUE.equals(dispositivo.getEstado())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Dispositivo já está ligado."));
        }

        dispositivoService.ligar(id);
        return ResponseEntity.ok(Map.of("message", "Dispositivo ligado com sucesso"));
    }

    @PutMapping("/{id}/desligar")
    public ResponseEntity<?> desligarDispositivo(@PathVariable Long id) {
        Dispositivo dispositivo = dispositivoService.buscarPorId(id);

        if (Boolean.FALSE.equals(dispositivo.getEstado()) || dispositivo.getEstado() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Dispositivo já está desligado."));
        }

        dispositivoService.desligar(id);
        return ResponseEntity.ok(Map.of("message", "Dispositivo desligado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        dispositivoService.deletar(id);
        return ResponseEntity.ok(Map.of("message", "Dispositivo apagado com sucesso"));
    }
}
