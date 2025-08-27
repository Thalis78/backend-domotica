package com.back.domotica.services;

import com.back.domotica.entities.Cena;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.CenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Cena atualizar(Long id, Cena cenaAtualizada) {
        return cenaRepository.findById(id)
                .map(cena -> {
                    cena.setNome(cenaAtualizada.getNome());
                    cena.setAtiva(cenaAtualizada.isAtiva());
                    return cenaRepository.save(cena);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cena não encontrada com id: " + id));
    }

    public Cena ligar(Long id) {
        Cena cena = buscarPorId(id);
        cena.ligar();
        return cenaRepository.save(cena);
    }

    public Cena desligar(Long id) {
        Cena cena = buscarPorId(id);
        cena.desligar();
        return cenaRepository.save(cena);
    }

    public void deletar(Long id) {
        if (!cenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cena não encontrada com id: " + id);
        }
        cenaRepository.deleteById(id);
    }

    public Cena buscarPorId(Long id) {
        return cenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cena não encontrada com id: " + id));
    }
}
