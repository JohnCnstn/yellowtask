package com.johncnstn.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Setter
    @Getter
    private long id;

    @Column(name = "imgURL")
    @Setter
    @Getter
    private String imgURL;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @Setter
    @Getter
    private User user;
}
