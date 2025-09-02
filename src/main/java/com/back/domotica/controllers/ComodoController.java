package com.back.domotica.controllers;

import com.back.domotica.entities.Comodo;
import com.back.domotica.services.ComodoService;
import com.back.domotica.services.DispositivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comodos")
public class ComodoController {


    private final ComodoService comodoService;

    @Autowired
    public ComodoController(ComodoService comodoService) {
        this.comodoService = comodoService;
    }

    @GetMapping
    public ResponseEntity<List<Comodo>> listarTodos() {
        List<Comodo> comodos = comodoService.listarTodos();
        return ResponseEntity.ok(comodos);
    }

    @PostMapping
    public ResponseEntity<Comodo> criar(@Valid @RequestBody Comodo comodo) {
        Comodo salvo = comodoService.salvar(comodo);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comodo> atualizar(@PathVariable Long id, @Valid @RequestBody Comodo comodo) {
        Comodo atualizado = comodoService.atualizar(id, comodo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        comodoService.deletar(id);
        return ResponseEntity.ok().body(Map.of("message", "Cômodo apagado com sucesso"));
    }
}
