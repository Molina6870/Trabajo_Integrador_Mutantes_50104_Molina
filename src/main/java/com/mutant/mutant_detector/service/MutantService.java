package com.mutant.mutant_detector.service;

import com.mutant.mutant_detector.model.DnaRecord;
import com.mutant.mutant_detector.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MutantService {

    private final MutantDetector detector;
    private final DnaRecordRepository dnaRecordRepository;

    // Inyección de dependencias para Detector y Repository
    public MutantService(MutantDetector detector, DnaRecordRepository dnaRecordRepository) {
        this.detector = detector;
        this.dnaRecordRepository = dnaRecordRepository;
    }

    /**
     * Verifica si un ADN es mutante y guarda el resultado en la base de datos (o lo recupera si ya existe).
     * @param dna Array de Strings que representa la secuencia de ADN.
     * @return true si es mutante, false si es humano.
     */
    public boolean isMutant(String[] dna) {
        // 1. Calcular Hash (para usarlo como ID y evitar duplicados)
        String dnaHash = Arrays.toString(dna);

        // 2. Intentar recuperar el registro de la DB (Optimización: evitar recálculo)
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findById(dnaHash);

        if (existingRecord.isPresent()) {
            // El ADN ya fue analizado. Devolver el resultado guardado.
            return existingRecord.get().isMutant();
        } else {
            // 3. El ADN es nuevo. Ejecutar la detección.
            boolean isMutant = detector.isMutant(dna);

            // 4. Guardar el nuevo registro en la DB
            DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
            dnaRecordRepository.save(newRecord);

            return isMutant;
        }
    }
}
