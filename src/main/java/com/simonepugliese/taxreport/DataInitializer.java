package com.simonepugliese.taxreport;

import com.simonepugliese.taxreport.model.CategoriaSpesa;
import com.simonepugliese.taxreport.model.TipoDocumento;
import com.simonepugliese.taxreport.repository.CategoriaSpesaRepository;
import com.simonepugliese.taxreport.repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaSpesaRepository catRepo;
    private final TipoDocumentoRepository tipoRepo;

    @Override
    public void run(String... args) throws Exception {
        // Se non ci sono categorie, creiamole
        if (catRepo.count() == 0) {
            CategoriaSpesa c1 = new CategoriaSpesa();
            c1.setId("SPESA_MEDICA");
            c1.setNomeLeggibile("Spese Mediche e Sanitarie");

            CategoriaSpesa c2 = new CategoriaSpesa();
            c2.setId("VETERINARIO");
            c2.setNomeLeggibile("Spese Veterinarie");

            CategoriaSpesa c3 = new CategoriaSpesa();
            c3.setId("UNIVERSITA");
            c3.setNomeLeggibile("Istruzione Universitaria");

            catRepo.saveAll(Arrays.asList(c1, c2, c3));
        }

        // Se non ci sono tipi documento, creiamoli
        if (tipoRepo.count() == 0) {
            TipoDocumento t1 = new TipoDocumento();
            t1.setId("FATTURA");
            t1.setNomeLeggibile("Fattura");

            TipoDocumento t2 = new TipoDocumento();
            t2.setId("SCONTRINO");
            t2.setNomeLeggibile("Scontrino Parlante");

            TipoDocumento t3 = new TipoDocumento();
            t3.setId("PRESCRIZIONE");
            t3.setNomeLeggibile("Prescrizione Medica/Ricetta");

            tipoRepo.saveAll(Arrays.asList(t1, t2, t3));
        }
    }
}