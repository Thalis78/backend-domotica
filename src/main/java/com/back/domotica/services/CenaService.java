package com.back.domotica.services;

import com.back.domotica.entities.Cena;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.CenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CenaService {

    private final CenaRepository cenaRepository;

    @Autowired
    public CenaService(CenaRepository cenaRepository) {
        this.cenaRepository = cenaRepository;
    }

    public Cena salvar(Cena cena) {
        return cenaRepository.save(cena);
    }

    public List<Cena> listarTodas() {
        return cenaRepository.findAll();
    }

    public Optional<Cena> buscarPorId(Long id) {
        return cenaRepository.findById(id);
    }

    public Cena atualizar(Long id, Cena cenaAtualizada) {
        return cenaRepository.findById(id)
                .map(cena -> {
                    cena.setNome(cenaAtualizada.getNome());
                    cena.setAtiva(cenaAtualizada.isAtiva());
                    return cenaRepository.save(cena);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cena não encontrada com id: " + id));
    }

    public void deletar(Long id) {
        if (!cenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cena não encontrada com id: " + id);
        }
        cenaRepository.deleteById(id);
    }
}
