package com.x.x.models.dao;

import com.x.x.models.dto.ReservationConsultDTO;
import com.x.x.models.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface RservationDao extends JpaRepository<Reservation, Long> {

    @Query("select new com.x.x.models.dto.ReservationConsultDTO(e.id, e.starTime, e.endTime, e.idUSer, e.idSpace) from Reservation e ")
    List<ReservationConsultDTO> findAllReservas();

    @Query("select new com.x.x.models.dto.ReservationConsultDTO(e.id, e.starTime, e.endTime, e.idUSer, e.idSpace) from Reservation e " +
            "where e.id = ?1")
    Optional<ReservationConsultDTO> findAllReservasForId(Long id);

    @Query("SELECT r FROM Reservation r WHERE r.idSpace = :idSpace AND "
            + "((r.starTime < :endTime AND r.endTime > :startTime) OR "
            + "(r.starTime > :startTime AND r.starTime < :endTime))")
    List<Reservation> findBySpaceAndTimeOverlap(Long idSpace, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT r FROM Reservation r WHERE r.idUSer = :idUser AND "
            + "((r.starTime < :endTime AND r.endTime > :startTime) OR "
            + "(r.starTime > :startTime AND r.starTime < :endTime))")
    List<Reservation> findByUserAndTimeOverlap(Long idUser, LocalDateTime startTime, LocalDateTime endTime);
}
