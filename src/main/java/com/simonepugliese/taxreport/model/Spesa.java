package com.simonepugliese.taxreport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Spesa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private Persona persona;

    @ManyToOne(optional = false)
    private CategoriaSpesa categoria;

    private String descrizione; // Es: "Visita oculistica"

    private BigDecimal importo; // Fondamentale per il report tasse!

    @Column(nullable = false)
    private LocalDate dataSpesa;

    @Column(nullable = false)
    private String annoCompetenza; // Es: "2024"

    // Stato calcolato (VALIDA, INCOMPLETA).
    // Lo salviamo come stringa per semplicità, o potremmo usare un Enum interno.
    private String statoValidazione = "INCOMPLETA";

    // Una spesa ha molti documenti
    @OneToMany(mappedBy = "spesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documenti = new ArrayList<>();
}