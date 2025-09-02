package com.back.domotica.services;

import com.back.domotica.entities.Comodo;
import com.back.domotica.entities.Dispositivo;
import com.back.domotica.entities.Grupo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.ComodoRepository;
import com.back.domotica.repositories.GrupoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComodoService {

    private final DispositivoService dispositivoService;
    private final GrupoRepository grupoRepository;
    private final ComodoRepository comodoRepository;

    @Autowired
    public ComodoService(
            DispositivoService dispositivoService,
            GrupoRepository grupoRepository,
            ComodoRepository comodoRepository) {

        this.dispositivoService = dispositivoService;
        this.grupoRepository = grupoRepository;
        this.comodoRepository = comodoRepository;
    }


    public Comodo salvar(Comodo comodo) {
        return comodoRepository.save(comodo);
    }

    public Comodo buscarPorId(Long id) {
        return comodoRepository.getById(id);
    }

    public List<Comodo> listarTodos() {
        return comodoRepository.findAll();
    }

    public Comodo atualizar(Long id, Comodo comodoAtualizado) {
        return comodoRepository.findById(id)
                .map(comodo -> {
                    comodo.setNome(comodoAtualizado.getNome());
                    return comodoRepository.save(comodo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cômodo não encontrado com id: " + id));
    }

    @Transactional
    public void deletar(Long idComodo) {
        Comodo comodo = buscarPorId(idComodo);

        for (Dispositivo dispositivo : comodo.getDispositivos()) {
            List<Grupo> grupos = grupoRepository.findGruposByDispositivoId(dispositivo.getIdDispositivo());

            for (Grupo grupo : grupos) {
                grupo.getDispositivos().removeIf(d -> d.getIdDispositivo().equals(dispositivo.getIdDispositivo()));
                grupoRepository.save(grupo);
            }

            dispositivoService.deletar(dispositivo.getIdDispositivo());
        }

        comodoRepository.delete(comodo);
    }


}
