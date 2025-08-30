package com.back.domotica.services;

import com.back.domotica.entities.Dispositivo;
import com.back.domotica.entities.Grupo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.DispositivoRepository;
import com.back.domotica.repositories.GrupoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    public Dispositivo salvar(Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    public List<Dispositivo> listarTodos() {
        return dispositivoRepository.findAll();
    }

    public Dispositivo buscarPorId(Long id) {
        return dispositivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com id: " + id));
    }

    public Dispositivo atualizar(Long id, Dispositivo dispositivoAtualizado) {
        return dispositivoRepository.findById(id)
                .map(dispositivo -> {
                    dispositivo.setNome(dispositivoAtualizado.getNome());
                    dispositivo.setEstado(dispositivoAtualizado.getEstado());
                    dispositivo.setComodo(dispositivoAtualizado.getComodo());
                    return dispositivoRepository.save(dispositivo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com id: " + id));
    }

    public Dispositivo ligar(Long id) {
        Dispositivo dispositivo = buscarPorId(id);
        dispositivo.ligar();
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo desligar(Long id) {
        Dispositivo dispositivo = buscarPorId(id);
        dispositivo.desligar();
        return dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public void deletar(Long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com id: " + id));

        List<Grupo> grupos = grupoRepository.findGruposByDispositivoId(id);
        for (Grupo grupo : grupos) {
            grupo.getDispositivos().remove(dispositivo);
            grupoRepository.save(grupo);
        }

        dispositivoRepository.delete(dispositivo);
    }

}
