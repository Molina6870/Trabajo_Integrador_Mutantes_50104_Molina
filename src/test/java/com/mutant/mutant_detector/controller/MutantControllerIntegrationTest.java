package com.mutant.mutant_detector.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutant.mutant_detector.dto.MutantRequest;
import com.mutant.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays; // <--- ESTE IMPORT ES NECESARIO

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MutantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DnaRecordRepository dnaRecordRepository;

    /**
     * Limpia la base de datos H2 antes de cada test.
     */
    @BeforeEach
    @AfterEach
    void setup() {
        dnaRecordRepository.deleteAll();
    }

    // --- TEST POST /mutant ---

    @Test
    void testIsMutant_Success_200() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        // CORRECCIÓN: Usar Arrays.asList()
        MutantRequest request = new MutantRequest(Arrays.asList(dna));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()); // 200 OK
    }

    @Test
    void testIsHuman_Forbidden_403() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTCT", "AGAC"};
        // CORRECCIÓN: Usar Arrays.asList()
        MutantRequest request = new MutantRequest(Arrays.asList(dna));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden()); // 403 Forbidden
    }

    @Test
    void testInvalidDna_BadRequest_400() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTT"};
        // CORRECCIÓN: Usar Arrays.asList()
        MutantRequest request = new MutantRequest(Arrays.asList(dna));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // 400 Bad Request
                .andExpect(jsonPath("$.error", is("La matriz debe ser NxN (Cuadrada).")));
    }

    // --- TEST GET /stats ---

    @Test
    void testGetStats_WithDataAndRatio() throws Exception {
        // 1. Enviar 1 Mutante
        String[] mutantDna = {"AAAA", "TGCT", "CGTA", "TACG"};
        // CORRECCIÓN: Usar Arrays.asList()
        MutantRequest mutantRequest = new MutantRequest(Arrays.asList(mutantDna));
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mutantRequest)))
                .andExpect(status().isOk());

        // 2. Enviar 1 Humano
        String[] humanDna = {"ATGC", "CAGT", "TTCT", "AGAC"};
        // CORRECCIÓN: Usar Arrays.asList()
        MutantRequest humanRequest = new MutantRequest(Arrays.asList(humanDna));
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(humanRequest)))
                .andExpect(status().isForbidden());

        // 3. Obtener Estadísticas: Ratio 1/1 = 1.0
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna", is(1)))
                .andExpect(jsonPath("$.count_human_dna", is(1)))
                .andExpect(jsonPath("$.ratio", is(1.0)));
    }

    @Test
    void testIsMutant_DuplicateDna_200() throws Exception {
        // Prueba de que no se guarda doble.
        String[] mutantDna = {"AAAA", "TGCT", "CGTA", "TACG"};
        // CORRECCIÓN: Usar Arrays.asList()
        MutantRequest mutantRequest = new MutantRequest(Arrays.asList(mutantDna));

        // Primera llamada (calcula y guarda)
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mutantRequest)))
                .andExpect(status().isOk());

        // Segunda llamada con el mismo ADN (debe recuperar de DB, no recalcular)
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mutantRequest)))
                .andExpect(status().isOk());
    }
}