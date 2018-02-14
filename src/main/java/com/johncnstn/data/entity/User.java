package com.johncnstn.data.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Setter
    @Getter
    private long id;

    @Column(name = "userName")
    @Setter
    @Getter
    private String userName;

    @Column(name = "password")
    @Setter
    @Getter
    private String password;

//    @OneToMany(mappedBy="user")
//    private Set<Entry> entries;
}
