package com.back.domotica.repositories;

import com.back.domotica.entities.AcaoCena;
import com.back.domotica.entities.Dispositivo;
import com.back.domotica.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcaoCenaRepository extends JpaRepository<AcaoCena, Long> {

    List<AcaoCena> findByGrupos_IdGrupo(Long idGrupo);

    List<AcaoCena> findByDispositivos_IdDispositivo(Long idDispositivo);

    void deleteByCenaIdCena(Long cenaId);

}
