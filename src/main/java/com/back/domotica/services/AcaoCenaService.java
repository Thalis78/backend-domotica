package com.back.domotica.services;

import com.back.domotica.entities.AcaoCena;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.AcaoCenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcaoCenaService {

    private final AcaoCenaRepository acaoCenaRepository;

    @Autowired
    public AcaoCenaService(AcaoCenaRepository acaoCenaRepository) {
        this.acaoCenaRepository = acaoCenaRepository;
    }

    public AcaoCena salvar(AcaoCena acaoCena) {
        return acaoCenaRepository.save(acaoCena);
    }

    public List<AcaoCena> listarTodas() {
        return acaoCenaRepository.findAll();
    }

    public Optional<AcaoCena> buscarPorId(Long id) {
        return acaoCenaRepository.findById(id);
    }

    public AcaoCena atualizar(Long id, AcaoCena acaoAtualizada) {
        return acaoCenaRepository.findById(id)
                .map(acao -> {
                    acao.setNome(acaoAtualizada.getNome());
                    acao.setOrdem(acaoAtualizada.getOrdem());
                    acao.setIntervaloSegundos(acaoAtualizada.getIntervaloSegundos());
                    acao.setEstadoDesejado(acaoAtualizada.isEstadoDesejado());
                    acao.setCena(acaoAtualizada.getCena());
                    acao.setDispositivo(acaoAtualizada.getDispositivo());
                    return acaoCenaRepository.save(acao);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Ação de Cena não encontrada com id: " + id));
    }

    public void deletar(Long id) {
        if (!acaoCenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ação de Cena não encontrada com id: " + id);
        }
        acaoCenaRepository.deleteById(id);
    }
}
