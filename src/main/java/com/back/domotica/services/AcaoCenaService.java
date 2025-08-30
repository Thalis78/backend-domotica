package com.back.domotica.services;

import com.back.domotica.entities.AcaoCena;
import com.back.domotica.exceptions.ResourceNotFoundException;
import com.back.domotica.repositories.AcaoCenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public AcaoCena atualizar(Long id, AcaoCena acaoAtualizada) {
        return acaoCenaRepository.findById(id)
                .map(acao -> {
                    acao.setNome(acaoAtualizada.getNome());
                    acao.setOrdem(acaoAtualizada.getOrdem());
                    acao.setIntervaloSegundos(acaoAtualizada.getIntervaloSegundos());
                    acao.setEstadoDesejado(acaoAtualizada.isEstadoDesejado());
                    acao.setCena(acaoAtualizada.getCena());
                    acao.setDispositivos(acaoAtualizada.getDispositivos());
                    acao.setGrupos(acaoAtualizada.getGrupos());
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

    public AcaoCena executar(Long id) {
        AcaoCena acao = acaoCenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ação de Cena não encontrada com id: " + id));

        acao.executar();
        return acaoCenaRepository.save(acao);
    }
}
