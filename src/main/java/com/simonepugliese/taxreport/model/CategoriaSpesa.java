package com.simonepugliese.taxreport.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CategoriaSpesa {

    @Id
    private String id; // Es: "SPESA_MEDICA"

    @Column(nullable = false)
    private String nomeLeggibile; // Es: "Spese Mediche e Sanitarie"

    private String descrizione; // Es: "Farmaci, visite specialistiche..."
}