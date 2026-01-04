package com.simonepugliese.taxreport.service;

import com.simonepugliese.taxreport.model.*;
import com.simonepugliese.taxreport.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GestioneDatiService {

    private final SpesaRepository spesaRepository;
    private final DocumentoRepository documentoRepository;
    private final ValidazioneService validazioneService;

    // Path base dove salvare i file (configurabile, per ora hardcoded per semplicità)
    private final Path rootLocation = Path.of("taxreport-data");

    @Transactional
    public Spesa registraSpesa(Spesa spesa) {
        // 1. Salviamo la spesa grezza
        // (Nota: qui potremmo fare controlli se la persona esiste, ecc.)

        // 2. Calcoliamo subito lo stato di validazione
        String stato = validazioneService.calcolaStatoSpesa(spesa);
        spesa.setStatoValidazione(stato);

        return spesaRepository.save(spesa);
    }

    @Transactional
    public Documento aggiungiDocumento(UUID spesaId, TipoDocumento tipo, MultipartFile fileFisico) throws IOException {
        // 1. Recupera la spesa
        Spesa spesa = spesaRepository.findById(spesaId)
                .orElseThrow(() -> new RuntimeException("Spesa non trovata: " + spesaId));

        // 2. Crea la cartella fisica se non esiste (Es: taxreport-data/2024/RSSMRA...)
        if (!Files.exists(rootLocation)) Files.createDirectories(rootLocation);

        // Genera un nome file univoco per evitare sovrascritture
        String filename = System.currentTimeMillis() + "_" + fileFisico.getOriginalFilename();
        Path destination = rootLocation.resolve(filename);

        // 3. Salva il file su disco
        Files.copy(fileFisico.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        // 4. Crea il record nel DB
        Documento doc = new Documento();
        doc.setSpesa(spesa);
        doc.setTipoDocumento(tipo);
        doc.setNomeFileOriginale(fileFisico.getOriginalFilename());
        doc.setPercorsoFile(destination.toString());

        spesa.getDocumenti().add(doc); // Aggiorna la lista in memoria
        documentoRepository.save(doc);

        // 5. Ricalcola lo stato della spesa (ora che c'è un documento nuovo potrebbe essere COMPLETA!)
        String nuovoStato = validazioneService.calcolaStatoSpesa(spesa);
        spesa.setStatoValidazione(nuovoStato);
        spesaRepository.save(spesa);

        return doc;
    }
}