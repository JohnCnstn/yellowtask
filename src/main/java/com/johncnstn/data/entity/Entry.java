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
    @Column(name = "id", nullable = false)
    @Setter
    @Getter
    private long id;

    @Column(name = "distance")
    @Setter
    @Getter
    private long distance;

    @Column(name = "raceTime")
    @Setter
    @Getter
    private Time raceTime;

    @Column(name = "startRaceDateTime")
    @Setter
    @Getter
    private LocalDateTime startRaceDateTime;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Entry() {
    }
}
