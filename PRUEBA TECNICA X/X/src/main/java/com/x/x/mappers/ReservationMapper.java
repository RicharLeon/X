package com.x.x.mappers;

import com.x.x.models.dto.ReservationConsultDTO;
import com.x.x.models.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel ="spring" )
public interface ReservationMapper {

    ReservationMapper INSTANCIA = Mappers.getMapper(ReservationMapper.class);
    Reservation dtoPersistenciaToEntity(ReservationConsultDTO reservationConsultDTO);

}
