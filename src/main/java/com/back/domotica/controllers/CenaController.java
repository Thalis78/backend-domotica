package com.back.domotica.controllers;

import com.back.domotica.entities.Cena;
import com.back.domotica.services.CenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Cena>> listarTodas() {
        List<Cena> cenas = cenaService.listarTodas();
        return ResponseEntity.ok(cenas);
    }

    @PostMapping
    public ResponseEntity<Cena> criar(@Valid @RequestBody Cena cena) {
        Cena salvo = cenaService.salvar(cena);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cena> atualizar(@PathVariable Long id, @Valid @RequestBody Cena cena) {
        Cena atualizado = cenaService.atualizar(id, cena);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        cenaService.deletar(id);
        return ResponseEntity.ok("Cena apagada com sucesso");
    }

    @PutMapping("/{id}/ligar")
    public ResponseEntity<String> ligar(@PathVariable Long id) {
        Cena cena = cenaService.buscarPorId(id);

        if (cena.isAtiva()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cena já está ligada.");
        }

        cenaService.ligar(id);
        return ResponseEntity.ok("Cena ligada com sucesso");
    }

    @PutMapping("/{id}/desligar")
    public ResponseEntity<String> desligar(@PathVariable Long id) {
        Cena cena = cenaService.buscarPorId(id);

        if (!cena.isAtiva()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cena já está desligada.");
        }

        cenaService.desligar(id);
        return ResponseEntity.ok("Cena desligada com sucesso");
    }
}
