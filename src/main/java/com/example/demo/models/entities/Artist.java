package com.example.demo.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private int id;

    private String name;

    @Column(name = "artistic_name")
    private String artisticName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String nationality;


    public Artist(String name, String artisticName, LocalDate dateOfBirth, String nationality) {
        this.name = name;
        this.artisticName = artisticName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }
}
