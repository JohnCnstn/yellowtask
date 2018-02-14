package com.johncnstn.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "distance")
    @Getter
    @Setter
    private long distance;

    @Column(name = "raceTime")
    @Getter
    @Setter
    private Time raceTime;

    @Column(name = "startRaceDateTime")
    @Getter
    @Setter
    private LocalDateTime startRaceDateTime;
}
