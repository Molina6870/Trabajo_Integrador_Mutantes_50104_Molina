package com.mutant.mutant_detector.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MutantRequest {

    @NotEmpty(message = "El campo DNA no puede estar vacío")
    private List<String> dna;

    public MutantRequest(java.util.List <String> dna){
            this.dna = dna;
    }

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }

}
