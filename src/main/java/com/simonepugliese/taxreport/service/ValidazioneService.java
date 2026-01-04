package com.simonepugliese.taxreport.service;

import com.simonepugliese.taxreport.model.*;
import com.simonepugliese.taxreport.repository.RegolaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok: crea il costruttore per iniettare i repository
public class ValidazioneService {

    private final RegolaRepository regolaRepository;

    public String calcolaStatoSpesa(Spesa spesa) {
        // 1. Cerchiamo se c'è una regola per questa categoria in questo anno
        Optional<Regola> regolaOpt = regolaRepository.findByCategoriaSpesaAndAnno(
                spesa.getCategoria(),
                spesa.getAnnoCompetenza()
        );

        // Se non c'è nessuna regola definita, assumiamo che la spesa sia OK (o da controllare manuale)
        if (regolaOpt.isEmpty()) {
            return "DA_VERIFICARE";
        }

        Regola regola = regolaOpt.get();
        List<TipoDocumento> richiesti = regola.getDocumentiRichiesti();

        // Se la regola non richiede documenti, è COMPLETA
        if (richiesti.isEmpty()) {
            return "COMPLETA";
        }

        // 2. Vediamo quali documenti abbiamo caricato finora
        Set<String> idsTipiPresenti = spesa.getDocumenti().stream()
                .map(doc -> doc.getTipoDocumento().getId())
                .collect(Collectors.toSet());

        // 3. Controllo: abbiamo tutto?
        for (TipoDocumento richiesto : richiesti) {
            if (!idsTipiPresenti.contains(richiesto.getId())) {
                return "INCOMPLETA"; // Manca qualcosa!
            }
        }

        return "COMPLETA";
    }
}