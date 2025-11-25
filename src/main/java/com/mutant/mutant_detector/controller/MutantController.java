package com.mutant.mutant_detector.controller;

import com.mutant.mutant_detector.dto.MutantRequest;
import com.mutant.mutant_detector.dto.StatsResponse;
import com.mutant.mutant_detector.service.MutantService;
import com.mutant.mutant_detector.service.StatsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// El RequestMapping es solo para el endpoint POST: /mutant
// Para el GET /stats usaremos un mapping completo en el método.
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    // Inyección de dependencias por constructor
    public MutantController(MutantService mutantService, StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    @GetMapping("/")
    public String home() {
        return "Bienvenido a la API de detección de mutantes. Usa el endpoint /mutant para verificar un ADN.";
    }

    /**
     * Endpoint POST /mutant - Determina si el ADN es mutante.
     * Devuelve 200 OK si es mutante, 403 Forbidden si es humano.
     */
    @PostMapping("/mutant")
    public ResponseEntity<?> isMutant(@Valid @RequestBody MutantRequest req) {

        // El MutantService ahora se encarga de llamar al detector y guardar en DB.
        boolean isMutant = mutantService.isMutant(req.getDna().toArray(new String[0]));

        if (isMutant) {
            return ResponseEntity.ok().build();        // 200 OK
        } else {
            return ResponseEntity.status(403).build(); // 403 Forbidden
        }
    }

    /**
     * Endpoint GET /stats - Devuelve las estadísticas de ADN.
     */
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getDnaStats();
        return ResponseEntity.ok(stats);
    }
}