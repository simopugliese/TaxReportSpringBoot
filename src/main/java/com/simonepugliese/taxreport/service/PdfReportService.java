package com.simonepugliese.taxreport.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.simonepugliese.taxreport.model.Spesa;
import com.simonepugliese.taxreport.repository.SpesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfReportService {

    private final SpesaRepository spesaRepository;

    public byte[] generaReportPdf(String anno, UUID personaId) {
        // 1. Recupera i dati
        List<Spesa> spese = spesaRepository.findByPersonaIdAndAnnoCompetenza(personaId, anno);

        // 2. Prepara il documento PDF
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Titolo
            Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titolo = new Paragraph("Riepilogo Spese Detraibili " + anno, fontTitolo);
            titolo.setAlignment(Element.ALIGN_CENTER);
            document.add(titolo);
            document.add(new Paragraph(" ")); // Spazio vuoto

            // Tabella a 5 colonne
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            // Larghezza colonne: Checkbox(10%), Data(15%), Categoria(30%), Descrizione(30%), Importo(15%)
            table.setWidths(new float[]{1, 1.5f, 3, 3, 1.5f});

            // Intestazioni
            addHeader(table, "[ ]"); // Per la spunta a penna
            addHeader(table, "Data");
            addHeader(table, "Categoria");
            addHeader(table, "Descrizione");
            addHeader(table, "Importo (€)");

            // Righe Dati
            for (Spesa s : spese) {
                addCell(table, ""); // Cella vuota per la spunta
                addCell(table, s.getDataSpesa().toString());
                addCell(table, s.getCategoria().getNomeLeggibile());
                addCell(table, s.getDescrizione() != null ? s.getDescrizione() : "-");
                addCell(table, s.getImporto() != null ? s.getImporto().toString() : "0.00");
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Errore generazione PDF", e);
        }

        return out.toByteArray();
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        table.addCell(cell);
    }
}