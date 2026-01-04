package com.simonepugliese.taxreport.repository;

import com.simonepugliese.taxreport.model.Spesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpesaRepository extends JpaRepository<Spesa, UUID> {

    // "Dammi tutte le spese del 2024"
    List<Spesa> findByAnnoCompetenza(String annoCompetenza);

    // "Dammi tutte le spese di Mario Rossi per il 2024"
    List<Spesa> findByPersonaIdAndAnnoCompetenza(UUID personaId, String annoCompetenza);
}