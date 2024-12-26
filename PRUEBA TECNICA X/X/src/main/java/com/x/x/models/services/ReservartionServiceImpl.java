package com.x.x.models.services;

import com.x.x.mappers.ReservationMapper;
import com.x.x.models.dao.RservationDao;
import com.x.x.models.dao.SpaceDao;
import com.x.x.models.dao.UsersDao;
import com.x.x.models.dto.ReservationConsultDTO;
import com.x.x.models.entity.Reservation;
import com.x.x.utils.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservartionServiceImpl implements IReservationService {

    private final RservationDao reservationDao;
    private final SpaceDao spaceDao;
    private final UsersDao usersDao;

    @Override
    public List<ReservationConsultDTO> getAllReservations() {
        return reservationDao.findAllReservas();
    }

    @Override
    public ReservationConsultDTO finbyId(Long id) {
        return reservationDao.findAllReservasForId(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("El User {0} no existe.", id)));
    }

    @Override
    public Reservation save(ReservationConsultDTO reservationConsultDTO) {
        Reservation reservationEntity = ReservationMapper.INSTANCIA.dtoPersistenciaToEntity(reservationConsultDTO);
        if (isOverlapping(reservationEntity)) {
            throw new IllegalArgumentException("La reserva se solapa con otra existente.");
        } else if (isUserDoubleBooking(reservationEntity)) {
            throw new IllegalArgumentException("El usuario ya tiene una reserva en otro espacio al mismo tiempo.");
        } else if (!isValidDuration(reservationEntity)) {
            throw new IllegalArgumentException("La reserva debe tener un tiempo mínimo de " +Constantes.MINIMUM_TIME_MINUTES
                    + " y máximo de "+Constantes.MAXIMUM_TIME_MINUTES);
        }
        return reservationDao.save(reservationEntity);
    }

    @Override
    public Reservation updateReservation(Long id, ReservationConsultDTO reservationConsultDTO) {
        Reservation existingReservation = reservationDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Reservacion con id {0} no existe", id)));

        Reservation updatedReservation = ReservationMapper.INSTANCIA.dtoPersistenciaToEntity(reservationConsultDTO);
        updatedReservation.setId(Integer.parseInt(String.valueOf(id)));

        if (isOverlapping(updatedReservation)) {
            throw new IllegalArgumentException("La reserva se solapa con otra existente.");
        } else if (isUserDoubleBooking(updatedReservation)) {
            throw new IllegalArgumentException("El usuario ya tiene una reserva en otro espacio al mismo tiempo.");
        } else if (!isValidDuration(updatedReservation)) {
            throw new IllegalArgumentException("La reserva debe tener un tiempo mínimo de " + Constantes.MINIMUM_TIME_MINUTES
                    + " y máximo de " + Constantes.MAXIMUM_TIME_MINUTES);
        }

        return reservationDao.save(updatedReservation);
    }




    @Override
    public void deleteById(Long id) {
        Reservation reservationR = reservationDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Reservacion con id {0} a eliminar no existe", id)));
        reservationDao.delete(reservationR);

    }

    private boolean isOverlapping(Reservation reservation) {
        List<Reservation> overlappingReservations = reservationDao.findBySpaceAndTimeOverlap(reservation.getIdSpace(), reservation.getStarTime(), reservation.getEndTime());
        return !overlappingReservations.isEmpty();
    }

    private boolean isUserDoubleBooking(Reservation reservation) {
        List<Reservation> userDoubleBookings = reservationDao.findByUserAndTimeOverlap(reservation.getIdUSer(), reservation.getStarTime(), reservation.getEndTime());
        return !userDoubleBookings.isEmpty();
    }

    private boolean isValidDuration(Reservation reservation) {
        long durationInMinutes = java.time.Duration.between(reservation.getStarTime(), reservation.getEndTime()).toMinutes();
        return durationInMinutes >= Constantes.MINIMUM_TIME_MINUTES && durationInMinutes <= Constantes.MAXIMUM_TIME_MINUTES;
    }


}
