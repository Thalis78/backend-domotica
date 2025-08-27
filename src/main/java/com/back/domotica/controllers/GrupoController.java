package com.back.domotica.controllers;

import com.back.domotica.entities.Grupo;
import com.back.domotica.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService grupoService;

    @Autowired
    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<Grupo> listarTodos() {
        return grupoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Grupo> criar(@Valid @RequestBody Grupo grupo) {
        Grupo salvo = grupoService.salvar(grupo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> atualizar(@PathVariable Long id, @Valid @RequestBody Grupo grupo) {
        Grupo atualizado = grupoService.atualizar(id, grupo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        grupoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
