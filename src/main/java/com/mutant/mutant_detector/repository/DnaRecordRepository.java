package com.mutant.mutant_detector.repository;

import com.mutant.mutant_detector.model.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, String> {
    // Método para contar todos los registros donde isMutant es verdadero
    long countByIsMutantTrue();

    // Método para contar todos los registros donde isMutant es falso
    long countByIsMutantFalse();

}
