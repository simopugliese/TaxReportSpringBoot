package com.simonepugliese.taxreport.repository;

import com.simonepugliese.taxreport.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, UUID> {

    // Trova tutti i documenti di una specifica spesa
    List<Documento> findBySpesaId(UUID spesaId);
}