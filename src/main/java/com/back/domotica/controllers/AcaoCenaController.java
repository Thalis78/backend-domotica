package com.back.domotica.controllers;

import com.back.domotica.entities.AcaoCena;
import com.back.domotica.entities.Cena;
import com.back.domotica.entities.Dispositivo;
import com.back.domotica.entities.Grupo;
import com.back.domotica.services.AcaoCenaService;
import com.back.domotica.services.CenaService;
import com.back.domotica.services.DispositivoService;
import com.back.domotica.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acaocenas")
public class AcaoCenaController {

    private final AcaoCenaService acaoCenaService;
    private final DispositivoService dispositivoService;
    private final GrupoService grupoService;
    private final CenaService cenaService;

    @Autowired
    public AcaoCenaController(AcaoCenaService acaoCenaService,DispositivoService dispositivoService,
                              GrupoService grupoService,
                              CenaService cenaService) {
        this.acaoCenaService = acaoCenaService;
        this.dispositivoService = dispositivoService;
        this.grupoService = grupoService;
        this.cenaService = cenaService;
    }

    @GetMapping
    public ResponseEntity<List<AcaoCena>> listarTodos() {
        List<AcaoCena> acoesCena = acaoCenaService.listarTodas();
        return ResponseEntity.ok(acoesCena);
    }

    @PostMapping
    public ResponseEntity<AcaoCena> criar(@Valid @RequestBody AcaoCena acaoCena) {
        Cena cena = cenaService.buscarPorId(acaoCena.getCena().getIdCena());
        if (cena == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        acaoCena.setCena(cena);

        List<Dispositivo> dispositivosValidos = validarDispositivos(acaoCena.getDispositivos());
        if (dispositivosValidos == null) {
            return ResponseEntity.badRequest().body(null);
        }
        acaoCena.setDispositivos(dispositivosValidos);

        List<Grupo> gruposValidos = validarGrupos(acaoCena.getGrupos());
        if (gruposValidos == null) {
            return ResponseEntity.badRequest().body(null);
        }
        acaoCena.setGrupos(gruposValidos);

        AcaoCena salvo = acaoCenaService.salvar(acaoCena);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoCena> atualizar(@PathVariable Long id, @Valid @RequestBody AcaoCena acaoCena) {
        AcaoCena atualizado = acaoCenaService.atualizar(id, acaoCena);
        if (atualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        acaoCenaService.deletar(id);
        return ResponseEntity.ok(Map.of("message", "Ação da cena apagada com sucesso"));
    }

    @PutMapping("/{id}/executar")
    public ResponseEntity<?> executar(@PathVariable Long id) {
        try {
            acaoCenaService.executar(id);
            return ResponseEntity.ok(Map.of("message", "Ação executada com sucesso"));
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Não é possível ativar a cena porque ela está inativa"));        }
    }


    private List<Dispositivo> validarDispositivos(List<Dispositivo> dispositivos) {
        if (dispositivos == null || dispositivos.isEmpty()) return new ArrayList<>();
        List<Dispositivo> dispositivosValidos = new ArrayList<>();
        for (Dispositivo dispositivo : dispositivos) {
            Long dispositivoId = dispositivo.getIdDispositivo();
            if (dispositivoId == null) return null;

            Dispositivo dispositivoEncontrado = dispositivoService.buscarPorId(dispositivoId);
            if (dispositivoEncontrado != null) {
                dispositivo.setNome(dispositivoEncontrado.getNome());
                dispositivo.setEstado(dispositivoEncontrado.getEstado());
                dispositivo.setComodo(dispositivoEncontrado.getComodo());
                dispositivosValidos.add(dispositivo);
            } else {
                return null;
            }
        }
        return dispositivosValidos;
    }

    private List<Grupo> validarGrupos(List<Grupo> grupos) {
        if (grupos == null || grupos.isEmpty()) return new ArrayList<>();
        List<Grupo> gruposValidos = new ArrayList<>();
        for (Grupo grupo : grupos) {
            Long grupoId = grupo.getIdGrupo();
            if (grupoId == null) return null;

            Grupo grupoEncontrado = grupoService.buscarPorId(grupoId);
            if (grupoEncontrado != null) {
                gruposValidos.add(grupoEncontrado);
            } else {
                return null;
            }
        }
        return gruposValidos;
    }
}
