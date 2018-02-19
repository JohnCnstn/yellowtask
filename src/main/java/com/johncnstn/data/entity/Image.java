package com.johncnstn.data.entity;

import java.net.URL;
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
    private URL imgURL;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @Setter
    @Getter
    private User user;
}
