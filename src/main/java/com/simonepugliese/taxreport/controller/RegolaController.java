package com.simonepugliese.taxreport.controller;

import com.simonepugliese.taxreport.model.Regola;
import com.simonepugliese.taxreport.repository.RegolaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regole")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegolaController {

    private final RegolaRepository regolaRepository;

    @GetMapping
    public List<Regola> getAllRegole() {
        return regolaRepository.findAll();
    }

    @PostMapping
    public Regola creaOAggiornaRegola(@RequestBody Regola regola) {
        // Qui potresti aggiungere logica per verificare se esiste già una regola
        // per quella categoria/anno e aggiornarla invece di crearne una doppia.
        return regolaRepository.save(regola);
    }

    @DeleteMapping("/{id}")
    public void eliminaRegola(@PathVariable java.util.UUID id) {
        regolaRepository.deleteById(id);
    }
}