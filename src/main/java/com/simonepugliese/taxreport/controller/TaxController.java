package com.simonepugliese.taxreport.controller;

import com.simonepugliese.taxreport.model.*;
import com.simonepugliese.taxreport.repository.*;
import com.simonepugliese.taxreport.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permette al frontend (ovunque sia) di chiamarci
public class TaxController {

    private final GestioneDatiService gestioneDatiService;
    private final PdfReportService pdfReportService; // Lo creeremo tra poco
    private final SpesaRepository spesaRepository;
    private final PersonaRepository personaRepository;
    private final CategoriaSpesaRepository categoriaSpesaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DocumentoRepository documentoRepository;

    // --- SETUP INIZIALE ---

    @GetMapping("/persone")
    public List<Persona> getPersone() {
        return personaRepository.findAll();
    }

    @GetMapping("/categorie")
    public List<CategoriaSpesa> getCategorie() {
        return categoriaSpesaRepository.findAll();
    }

    @GetMapping("/tipi-documento")
    public List<TipoDocumento> getTipiDocumento() {
        return tipoDocumentoRepository.findAll();
    }

    // --- GESTIONE SPESE ---

    @PostMapping("/spese")
    public Spesa creaSpesa(@RequestBody Spesa spesa) {
        // Il frontend invia il JSON della spesa, noi la salviamo
        return gestioneDatiService.registraSpesa(spesa);
    }

    @GetMapping("/spese/{anno}")
    public List<Spesa> getSpesePerAnno(@PathVariable String anno) {
        return spesaRepository.findByAnnoCompetenza(anno);
    }

    @PostMapping(value = "/spese/{id}/documenti", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Documento caricaDocumento(@PathVariable UUID id,
                                     @RequestParam("tipo") String tipoId,
                                     @RequestParam("file") MultipartFile file) throws IOException {

        // Recuperiamo il tipo documento dal DB (es. "FATTURA")
        TipoDocumento tipo = tipoDocumentoRepository.findById(tipoId)
                .orElseThrow(() -> new RuntimeException("Tipo documento non valido"));

        return gestioneDatiService.aggiungiDocumento(id, tipo, file);
    }

    // --- REPORTISTICA ---

    @GetMapping("/report/pdf/{anno}/{personaId}")
    public ResponseEntity<byte[]> scaricaPdf(@PathVariable String anno, @PathVariable UUID personaId) {
        try {
            byte[] pdfContent = pdfReportService.generaReportPdf(anno, personaId);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_" + anno + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/documenti/{id}/download")
    public ResponseEntity<Resource> downloadDocumento(@PathVariable UUID id) {
        Documento doc = documentoRepository.findById(id).orElseThrow();
        Resource file = gestioneDatiService.recuperaFileFisico(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getNomeFileOriginale() + "\"")
                .body(file);
    }

    // 2. ELIMINA SPESA
    @DeleteMapping("/spese/{id}")
    public ResponseEntity<Void> eliminaSpesa(@PathVariable UUID id) {
        gestioneDatiService.eliminaSpesa(id);
        return ResponseEntity.noContent().build();
    }

    // 3. AGGIORNA SPESA
    @PutMapping("/spese/{id}")
    public Spesa aggiornaSpesa(@PathVariable UUID id, @RequestBody Spesa spesa) {
        return gestioneDatiService.aggiornaSpesa(id, spesa);
    }
}