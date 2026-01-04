package com.simonepugliese.taxreport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "spesa_id") // FK verso la spesa
    private Spesa spesa;

    @ManyToOne(optional = false)
    private TipoDocumento tipoDocumento; // Es: Questo file è uno SCONTRINO

    private String nomeFileOriginale; // "scansione_1.pdf"

    // Dove abbiamo salvato il file sul Raspberry (es. "2024/RSSMRA/Medica/file_uuid.pdf")
    private String percorsoFile;
}