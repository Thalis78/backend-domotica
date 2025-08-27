package com.back.domotica.controllers;

import com.back.domotica.entities.Cena;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.services.CenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cenas")
public class CenaController {

    private final CenaService cenaService;

    @Autowired
    public CenaController(CenaService cenaService) {
        this.cenaService = cenaService;
    }

    @GetMapping
    public List<Cena> listarTodos() {
        return cenaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Cena> criar(@Valid @RequestBody Cena cena) {
        Cena salvo = cenaService.salvar(cena);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cena> atualizar(@PathVariable Long id, @Valid @RequestBody Cena cena) {
        Cena atualizado = cenaService.atualizar(id, cena);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cenaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ligar")
    public ResponseEntity<Cena> ligar(@PathVariable Long id) {
        Cena ligada = cenaService.ligar(id);
        return ResponseEntity.ok(ligada);
    }

    @PutMapping("/{id}/desligar")
    public ResponseEntity<Cena> desligar(@PathVariable Long id) {
        Cena desligada = cenaService.desligar(id);
        return ResponseEntity.ok(desligada);
    }
}
