package com.simonepugliese.taxreport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Regola {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String anno; // Es: "2024"

    @ManyToOne(optional = false)
    private CategoriaSpesa categoriaSpesa; // Es: SPESA_MEDICA

    // Una regola può richiedere molti tipi di documento (es. Fattura + Scontrino)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<TipoDocumento> documentiRichiesti = new ArrayList<>();
}