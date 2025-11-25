package com.mutant.mutant_detector.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    // Instancia del detector para las pruebas
    private final MutantDetector detector = new MutantDetector();

    // --- PRUEBAS DE MUTANTE (assertTrue) ---

    @Test
    void testMutantHorizontal() {
        // Secuencia AAAA en la primera fila
        String[] dna = {
                "AAAACG",
                "TGCATG",
                "CGTAAG",
                "TACGAA",
                "GATTCC",
                "TTCCGG"
        };
        assertTrue(detector.isMutant(dna), "Debe detectar la secuencia horizontal (AAAA)");
    }

    @Test
    void testMutantVertical() {
        // Secuencia AAAA en la primera columna
        String[] dna = {
                "ATGC",
                "ATGC",
                "ATGC",
                "ATGC"
        };
        assertTrue(detector.isMutant(dna), "Debe detectar la secuencia vertical (AAAA)");
    }

    @Test
    void testMutantDiagonalDerecha() {
        // Prueba fallida para mutante
        String[] dna = {
                "CATT",
                "ACGT",
                "AGCA",
                "TGCC"
        };
        assertTrue(detector.isMutant(dna), "Debe detectar la secuencia en diagonal principal (derecha)");
    }

    @Test
    void testMutantDiagonalIzquierda() {
        String[] dna = {
                "ATGG",
                "ATGG",
                "GGGA",
                "GGGC"
        };
        assertTrue(detector.isMutant(dna), "Debe detectar la secuencia en diagonal inversa (izquierda)");
    }

    @Test
    void testMutantMultipleSequences() {
        // Horizontal (AAAA) y Vertical (TTTT)
        String[] dna = {
                "AAAATG", // Horiz AAAA
                "CATTGA",
                "CAGTGA",
                "CATTGA",
                "TTTTGA", // Vert TTTT
                "GCGTTA"
        };
        assertTrue(detector.isMutant(dna), "Debe detectar dos secuencias y usar Early Exit");
    }

    // --- PRUEBAS DE NO MUTANTE (assertFalse) ---

    @Test
    void testNotMutant() {
        // Caso de humano
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTCT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna), "No debe encontrar secuencias mutantes (humano)");
    }

    @Test
    void testNotMutantNxN() {
        // Caso de humano 6x6
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "No debe encontrar secuencias en matriz grande");
    }

    // --- PRUEBAS DE VALIDACIÓN (assertThrows) ---

    @Test
    void testInvalidCharacters() {
        // Contiene 'X'
        String[] dna = {"AXGC", "ATGC", "ATGC", "ATGC"};
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna), "Debe lanzar excepción por caracter inválido");
    }

    @Test
    void testInvalidMatrixSize() {
        // No es NxN
        String[] dna = {"ATGC", "CAGT", "TTT"};
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna), "Debe lanzar excepción por matriz no cuadrada");
    }

    @Test
    void testEmptyOrNullDNA() {
        // DNA nulo
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(null), "Debe lanzar excepción por DNA nulo");

        // DNA vacío
        String[] emptyDna = {};
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(emptyDna), "Debe lanzar excepción por DNA vacío");
    }

    @Test
    void testMatrixTooSmall() {
        // 3x3 es muy pequeña para 4 secuencias
        String[] dna = {"ATC", "CAG", "TTT"};
        assertFalse(detector.isMutant(dna), "Matriz menor a 4x4 no puede ser mutante (false)");
    }
}
