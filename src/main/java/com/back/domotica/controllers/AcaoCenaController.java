package com.back.domotica.controllers;

import com.back.domotica.entities.AcaoCena;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.services.AcaoCenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acaocenas")
public class AcaoCenaController {

    private final AcaoCenaService acaoCenaService;

    @Autowired
    public AcaoCenaController(AcaoCenaService acaoCenaService) {
        this.acaoCenaService = acaoCenaService;
    }

    @GetMapping
    public List<AcaoCena> listarTodos() {
        return acaoCenaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<AcaoCena> criar(@Valid @RequestBody AcaoCena acaoCena) {
        AcaoCena salvo = acaoCenaService.salvar(acaoCena);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoCena> atualizar(@PathVariable Long id, @Valid @RequestBody AcaoCena acaoCena) {
        AcaoCena atualizado = acaoCenaService.atualizar(id, acaoCena);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        acaoCenaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/executar")
    public ResponseEntity<AcaoCena> executar(@PathVariable Long id) {
        AcaoCena executada = acaoCenaService.executar(id);
        return ResponseEntity.ok(executada);
    }
}
