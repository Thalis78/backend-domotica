package com.back.domotica.repositories;

import com.back.domotica.entities.AcaoCena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaoCenaRepository extends JpaRepository<AcaoCena, Long> {
}
