package com.mutant.mutant_detector.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DnaRecord {

    // Usaremos el hash del ADN como clave primaria para evitar duplicados.
    @Id
    @Column(unique = true, nullable = false)
    private String hashDna;

    @Column(nullable = false)
    private boolean isMutant;

    // Constructores, Getters y Setters
    public DnaRecord() {
    }

    public DnaRecord(String hashDna, boolean isMutant) {
        this.hashDna = hashDna;
        this.isMutant = isMutant;
    }

    public String getHashDna() {
        return hashDna;
    }

    public void setHashDna(String hashDna) {
        this.hashDna = hashDna;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }

}
