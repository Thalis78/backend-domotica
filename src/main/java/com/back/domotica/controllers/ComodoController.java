package com.back.domotica.controllers;

import com.back.domotica.entities.Comodo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.services.ComodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comodos")
public class ComodoController {

    private final ComodoService comodoService;

    @Autowired
    public ComodoController(ComodoService comodoService) {
        this.comodoService = comodoService;
    }

    @GetMapping
    public List<Comodo> listarTodos() {
        return comodoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comodo> buscarPorId(@PathVariable Long id) {
        return comodoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cômodo não encontrado com id: " + id));
    }

    @PostMapping
    public ResponseEntity<Comodo> criar(@Valid @RequestBody Comodo comodo) {
        Comodo salvo = comodoService.salvar(comodo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comodo> atualizar(@PathVariable Long id, @Valid @RequestBody Comodo comodo) {
        Comodo atualizado = comodoService.atualizar(id, comodo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        comodoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
