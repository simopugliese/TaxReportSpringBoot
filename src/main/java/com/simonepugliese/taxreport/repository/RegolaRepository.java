package com.simonepugliese.taxreport.repository;

import com.simonepugliese.taxreport.model.CategoriaSpesa;
import com.simonepugliese.taxreport.model.Regola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegolaRepository extends JpaRepository<Regola, UUID> {

    // Trova la regola per una certa categoria in un certo anno
    Optional<Regola> findByCategoriaSpesaAndAnno(CategoriaSpesa categoriaSpesa, String anno);
}