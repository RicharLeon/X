package com.x.x.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private int id;

    @Column(name = "start_time")
    private LocalDateTime starTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "id_user")
    private Long idUSer;

    @Column(name = "id_space")
    private Long idSpace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private Users Users;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_space", insertable = false, updatable = false)
    private Space space;

}
