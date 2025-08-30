package com.back.domotica.controllers;

import com.back.domotica.dtos.DispositivoDTO;
import com.back.domotica.entities.Comodo;
import com.back.domotica.entities.Dispositivo;
import com.back.domotica.services.ComodoService;
import com.back.domotica.services.DispositivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    @Autowired
    private ComodoService comodoService;

    @Autowired
    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @GetMapping
    public List<Dispositivo> listarTodos() {
        return dispositivoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Dispositivo> criar(@Valid @RequestBody DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = new Dispositivo();
        Comodo comodo = comodoService.buscarPorId(dispositivoDTO.getIdComodo());
        dispositivo.setNome(dispositivoDTO.getNome());
        dispositivo.setEstado(dispositivoDTO.getEstado());
        dispositivo.setComodo(comodo);
        Dispositivo salvo = dispositivoService.salvar(dispositivo);
        return ResponseEntity.ok(salvo);
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
    public ResponseEntity<String> ligarDispositivo(@PathVariable Long id) {
        Dispositivo dispositivo = dispositivoService.buscarPorId(id);

        if (dispositivo.getEstado()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dispositivo já está ligado.");
        }

        dispositivoService.ligar(id);
        return ResponseEntity.ok("Dispositivo ligado com sucesso");
    }

    @PutMapping("/{id}/desligar")
    public ResponseEntity<String> desligarDispositivo(@PathVariable Long id) {
        Dispositivo dispositivo = dispositivoService.buscarPorId(id);

        if (!dispositivo.getEstado()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dispositivo já está desligado.");
        }

        dispositivoService.desligar(id);
        return ResponseEntity.ok("Dispositivo desligado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        dispositivoService.deletar(id);
        return ResponseEntity.ok("Dispositivo apagado com sucesso");
    }
}
