package com.x.x.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationConsultDTO {
    private int id;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private Long idUSer;
    private Long idSpace;

}
