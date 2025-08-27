package com.back.domotica.services;

import com.back.domotica.entities.Comodo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.ComodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComodoService {

    private final ComodoRepository comodoRepository;

    @Autowired
    public ComodoService(ComodoRepository comodoRepository) {
        this.comodoRepository = comodoRepository;
    }

    public Comodo salvar(Comodo comodo) {
        return comodoRepository.save(comodo);
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

    public void deletar(Long id) {
        if (!comodoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cômodo não encontrado com id: " + id);
        }
        comodoRepository.deleteById(id);
    }
}
