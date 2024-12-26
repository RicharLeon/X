package com.x.x.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.x.models.dto.ReservationConsultDTO;
import com.x.x.models.entity.Reservation;
import com.x.x.models.services.IReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReservationConsultDTO reservationConsultDTO;
    private Reservation reservation;
    private Long validId = 1L;
    private Long invalidId = 99L;

    @Test
    void testGetAllReservations() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(new ReservationConsultDTO()));

        mockMvc.perform(get("/api/reservation/v1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetByIdReservations_validId() throws Exception {
        when(reservationService.finbyId(validId)).thenReturn(reservationConsultDTO);

        mockMvc.perform(get("/api/reservation/v1/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validId))
                .andExpect(jsonPath("$.someField").value("Test Reservation"));
    }

    @Test
    void testGetByIdReservations_invalidId() throws Exception {
        when(reservationService.finbyId(invalidId)).thenThrow(new RuntimeException("El User 99 no existe."));

        mockMvc.perform(get("/api/reservation/v1/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("El User 99 no existe."));
    }

    @Test
    void testAddReservation() throws Exception {
        Reservation mockReservation = new Reservation();
        ReservationConsultDTO reservationDTO = new ReservationConsultDTO();

        when(reservationService.save(any(ReservationConsultDTO.class))).thenReturn(mockReservation);

        mockMvc.perform(post("/api/reservation/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("La reserva ha sido creado con exito"));
    }

    @Test
    void testEliminarEmpleado() throws Exception {
        Mockito.doNothing().when(reservationService).deleteById(anyLong());

        mockMvc.perform(delete("/api/reservation/v1/empleado/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("La reserva ah sido eliminado con exito"));
    }
}
