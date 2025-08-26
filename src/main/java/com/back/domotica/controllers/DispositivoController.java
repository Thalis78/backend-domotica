package com.back.domotica.controllers;

import com.back.domotica.entities.Dispositivo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.services.DispositivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    @Autowired
    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @GetMapping
    public List<Dispositivo> listarTodos() {
        return dispositivoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispositivo> buscarPorId(@PathVariable Long id) {
        return dispositivoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com id: " + id));
    }

    @PostMapping
    public ResponseEntity<Dispositivo> criar(@Valid @RequestBody Dispositivo dispositivo) {
        Dispositivo salvo = dispositivoService.salvar(dispositivo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> atualizar(@PathVariable Long id, @Valid @RequestBody Dispositivo dispositivo) {
        Dispositivo atualizado = dispositivoService.atualizar(id, dispositivo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dispositivoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
