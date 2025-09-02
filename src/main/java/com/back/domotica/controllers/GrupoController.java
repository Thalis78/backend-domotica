package com.back.domotica.controllers;

import com.back.domotica.entities.Dispositivo;
import com.back.domotica.entities.Grupo;
import com.back.domotica.services.AcaoCenaService;
import com.back.domotica.services.DispositivoService;
import com.back.domotica.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grupos")
public class GrupoController {


    private final DispositivoService dispositivoService;
    private final AcaoCenaService acaoCenaService;
    private final GrupoService grupoService;

    @Autowired
    public GrupoController(DispositivoService dispositivoService, AcaoCenaService acaoCenaService, GrupoService grupoService) {
        this.dispositivoService = dispositivoService;
        this.acaoCenaService = acaoCenaService;
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<Grupo> listarTodos() {
        return grupoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Grupo> criar(@Valid @RequestBody Grupo grupo) {
        List<Dispositivo> dispositivosValidos = new ArrayList<>();

        for (Dispositivo dispositivo : grupo.getDispositivos()) {
            Long dispositivoId = dispositivo.getIdDispositivo();

            if (dispositivoId == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Dispositivo dispositivoEncontrado = dispositivoService.buscarPorId(dispositivoId);

            if (dispositivoEncontrado != null) {
                dispositivo.setNome(dispositivoEncontrado.getNome());
                dispositivo.setEstado(dispositivoEncontrado.getEstado());
                dispositivo.setComodo(dispositivoEncontrado.getComodo());
                dispositivosValidos.add(dispositivo);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        grupo.setDispositivos(dispositivosValidos);

        Grupo salvo = grupoService.salvar(grupo);

        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> atualizar(@PathVariable Long id, @Valid @RequestBody Grupo grupo) {
        Grupo atualizado = grupoService.atualizar(id, grupo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        acaoCenaService.removerGrupo(id);
        grupoService.deletar(id);
        return ResponseEntity.ok(Map.of("message", "Grupo apagado com sucesso"));
    }

    @PostMapping("/{id}/ligar")
    public ResponseEntity<?> ligarGrupo(@PathVariable Long id) {
        grupoService.ligarGrupo(id);
        return ResponseEntity.ok(Map.of("message", "Todos os dispositivos do grupo foram ligados."));
    }

    @PostMapping("/{id}/desligar")
    public ResponseEntity<?> desligarGrupo(@PathVariable Long id) {
        grupoService.desligarGrupo(id);
        return ResponseEntity.ok(Map.of("message", "Todos os dispositivos do grupo foram desligados."));
    }


}
