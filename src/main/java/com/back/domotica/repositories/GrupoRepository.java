package com.back.domotica.repositories;

import com.back.domotica.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    @Query(value = "SELECT g.* FROM grupo g " +
            "JOIN grupo_dispositivo gd ON g.id_grupo = gd.id_grupo " +
            "WHERE gd.id_dispositivo = :idDispositivo", nativeQuery = true)
    List<Grupo> findGruposByDispositivoId(@Param("idDispositivo") Long idDispositivo);

}
