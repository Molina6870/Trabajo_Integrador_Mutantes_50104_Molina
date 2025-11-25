package com.mutant.mutant_detector.service;

import com.mutant.mutant_detector.dto.StatsResponse;
import com.mutant.mutant_detector.repository.DnaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private DnaRecordRepository dnaRecordRepository;

    public StatsResponse getDnaStats() {
        long mutantDnaCount = dnaRecordRepository.countByIsMutantTrue(); // Cuenta mutantes
        long humanDnaCount = dnaRecordRepository.countByIsMutantFalse(); // Cuenta humanos

        double ratio = 0.0;
        if (humanDnaCount > 0) {
            ratio = (double) mutantDnaCount / humanDnaCount;
        }

        return new StatsResponse(mutantDnaCount, humanDnaCount, ratio);
    }

}
