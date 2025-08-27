package com.back.domotica.services;

import com.back.domotica.entities.Dispositivo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

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
                    dispositivo.setEstado(dispositivoAtualizado.isEstado());
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

    public void deletar(Long id) {
        if (!dispositivoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dispositivo não encontrado com id: " + id);
        }
        dispositivoRepository.deleteById(id);
    }
}
