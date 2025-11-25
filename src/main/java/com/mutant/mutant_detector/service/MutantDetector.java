package com.mutant.mutant_detector.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        // --- 1. VALIDACIÓN ---
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El ADN no puede ser nulo o vacío.");
        }

        int n = dna.length;
        if (n < SEQUENCE_LENGTH) { // Si el tamaño es menor a 4, no puede ser mutante.
            return false;
        }

        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            String row = dna[i];
            if (row.length() != n) {
                throw new IllegalArgumentException("La matriz debe ser NxN (Cuadrada).");
            }
            if (!row.matches("[ATCG]+")) {
                throw new IllegalArgumentException("El ADN solo puede contener A, T, C o G.");
            }
            matrix[i] = row.toCharArray();
        }

        // --- 2. DETECCIÓN ---
        int sequencesFound = 0;

        // Iteramos sobre las celdas de la matriz
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {

                // Intentar detección desde (x, y) en las 4 direcciones
                // Horizontal (dx=0, dy=1)
                if (checkDirection(matrix, x, y, n, 0, 1)) sequencesFound++;

                // Vertical (dx=1, dy=0)
                if (checkDirection(matrix, x, y, n, 1, 0)) sequencesFound++;

                // Diagonal Derecha (dx=1, dy=1)
                if (checkDirection(matrix, x, y, n, 1, 1)) sequencesFound++;

                // Diagonal Izquierda (dx=1, dy=-1)
                if (checkDirection(matrix, x, y, n, 1, -1)) sequencesFound++;

                // Early exit: Si ya encontramos dos secuencias distintas, es mutante.
                if (sequencesFound >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Revisa si existe una secuencia de 4 bases iguales iniciando en (x, y) en la dirección (dx, dy).
     * @return true si se encuentra la secuencia, false si no o si se sale de los límites.
     */
    private boolean checkDirection(char[][] matrix, int x, int y, int n, int dx, int dy) {
        // La secuencia de 4 termina en (endX, endY)
        int endX = x + (SEQUENCE_LENGTH - 1) * dx;
        int endY = y + (SEQUENCE_LENGTH - 1) * dy;

        // Comprobación de límites: Si el final de la secuencia se sale de la matriz (N x N)
        if (endX < 0 || endX >= n || endY < 0 || endY >= n) {
            return false;
        }

        char base = matrix[x][y]; // La base que buscamos repetir

        // k=1: compara la segunda posición con la base. k=3: compara la cuarta posición.
        for (int k = 1; k < SEQUENCE_LENGTH; k++) {
            if (matrix[x + k * dx][y + k * dy] != base) {
                return false; // La secuencia se rompió
            }
        }

        // Si llegamos aquí, encontramos 4 bases iguales
        return true;
    }


}

