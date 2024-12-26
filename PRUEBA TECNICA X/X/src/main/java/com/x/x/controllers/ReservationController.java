package com.x.x.controllers;

import com.x.x.models.dto.ReservationConsultDTO;
import com.x.x.models.entity.Reservation;
import com.x.x.models.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/reservation/v1")
public class ReservationController {
    Map<String, Object> response = new HashMap<>();

    @Autowired
    private IReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<?> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdReservations(@PathVariable long id) {
        return ResponseEntity.ok(reservationService.finbyId(id));
    }

    @PostMapping("")
    public ResponseEntity<?> addReservation(@RequestBody ReservationConsultDTO reservation) {
        try {
            Reservation empleadoNuevo = reservationService.save(reservation);
            response.put("mensaje", "La reserva ha sido creado con exito");
            response.put("Reserva", empleadoNuevo);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al realizar el insert en la base de datos", e) {
            };
        }
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
        try {
            reservationService.deleteById(id);
            response.put("mensaje", "La reserva ah sido eliminado con exito");
            response.put("Reserva", id);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al eliminar el user", e) {
            };
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody ReservationConsultDTO reservation) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservation);
            response.put("mensaje", "La reserva ha sido actualizado con exito");
            response.put("Reserva", updatedReservation);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al realizar el update en la base de datos", e) {
            };
        }
    }

}
