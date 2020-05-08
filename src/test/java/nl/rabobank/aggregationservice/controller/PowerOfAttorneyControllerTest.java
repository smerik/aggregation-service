package nl.rabobank.aggregationservice.controller;

import nl.rabobank.aggregationservice.model.PowerOfAttorney;
import nl.rabobank.aggregationservice.service.PowerOfAttorneyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PowerOfAttorneyController.class)
class PowerOfAttorneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PowerOfAttorneyService powerOfAttorneyService;

    @Test
    void getPowerOfAttorneys() throws Exception {
        // TODO: improve me
        final PowerOfAttorney powerOfAttorney = new PowerOfAttorney();
        when(powerOfAttorneyService.getPowerOfAttorneys()).thenReturn(Collections.singletonList(powerOfAttorney));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/power-of-attorneys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPowerOfAttorney() throws Exception {
        // TODO: improve me
        final PowerOfAttorney powerOfAttorney = new PowerOfAttorney();
        when(powerOfAttorneyService.getPowerOfAttorney("0001")).thenReturn(powerOfAttorney);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/power-of-attorneys/0001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}