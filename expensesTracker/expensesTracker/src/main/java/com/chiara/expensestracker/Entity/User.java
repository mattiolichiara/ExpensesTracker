package com.chiara.expensestracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user", schema = "tracker")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer idUser;
    @Column(name = "name", nullable = false)
    private Boolean name;
    @Column(name = "surname", nullable = false)
    private String surname;
}
