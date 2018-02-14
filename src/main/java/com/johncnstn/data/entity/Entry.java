package com.johncnstn.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "distance")
    private long distance;

    @Column(name = "raceTime")
    private Time raceTime;

    @Column(name = "startRaceDateTime")
    private LocalDateTime startRaceDateTime;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
