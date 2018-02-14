package com.johncnstn.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "userName")
    @Getter
    @Setter
    private String userName;

    @Column(name = "password")
    @Getter
    @Setter
    private String password;
}
