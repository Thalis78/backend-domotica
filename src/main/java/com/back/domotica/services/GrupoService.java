package com.back.domotica.services;

import com.back.domotica.entities.Grupo;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;

    @Autowired
    public GrupoService(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listarTodos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo não encontrado com id: " + id));
    }


    public Grupo atualizar(Long id, Grupo grupoAtualizado) {
        return grupoRepository.findById(id)
                .map(grupo -> {
                    grupo.setNome(grupoAtualizado.getNome());
                    grupo.setDispositivos(grupoAtualizado.getDispositivos());
                    return grupoRepository.save(grupo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Grupo não encontrado com id: " + id));
    }

    public void deletar(Long id) {
        if (!grupoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grupo não encontrado com id: " + id);
        }
        grupoRepository.deleteById(id);
    }
}
