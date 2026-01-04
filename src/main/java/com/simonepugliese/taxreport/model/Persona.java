package com.simonepugliese.taxreport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data // Lombok: crea getter, setter, toString da solo!
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;        // Es. "Mario Rossi"

    @Column(nullable = false, unique = true)
    private String codiceFiscale; // Es. "RSSMRA..."
}