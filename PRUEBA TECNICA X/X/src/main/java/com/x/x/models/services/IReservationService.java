package com.x.x.models.services;

import com.x.x.models.dto.ReservationConsultDTO;
import com.x.x.models.entity.Reservation;

import java.util.List;

public interface IReservationService {
    List<ReservationConsultDTO> getAllReservations();
    ReservationConsultDTO finbyId(Long id);
    Reservation save(ReservationConsultDTO reservationConsultDTO);
    void deleteById(Long id);
    Reservation updateReservation(Long id, ReservationConsultDTO reservationConsultDTO);

}
