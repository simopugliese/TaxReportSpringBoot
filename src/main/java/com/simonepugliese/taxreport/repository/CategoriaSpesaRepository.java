package com.simonepugliese.taxreport.repository;

import com.simonepugliese.taxreport.model.CategoriaSpesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaSpesaRepository extends JpaRepository<CategoriaSpesa, String> {
    // Non servono metodi extra per ora, le operazioni CRUD base bastano
}