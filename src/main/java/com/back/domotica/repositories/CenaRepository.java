package com.back.domotica.repositories;

import com.back.domotica.entities.Cena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenaRepository extends JpaRepository<Cena, Long> {
}
