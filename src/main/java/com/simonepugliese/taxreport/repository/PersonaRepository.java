package com.simonepugliese.taxreport.repository;

import com.simonepugliese.taxreport.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    // Trova una persona dal CF (utile per evitare duplicati in inserimento)
    Optional<Persona> findByCodiceFiscale(String codiceFiscale);
}