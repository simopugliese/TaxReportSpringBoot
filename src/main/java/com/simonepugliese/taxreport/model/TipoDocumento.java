package com.simonepugliese.taxreport.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TipoDocumento {

    @Id
    private String id; // Usiamo codici leggibili, es: "SCONTRINO", "FATTURA"

    @Column(nullable = false)
    private String nomeLeggibile; // Es: "Scontrino Fiscale parlante"
}