package com.example.demo.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;


@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private int id;

    @Column
    private String title;

    @Column(name = "duration_minutes")
    private Duration duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    private Genre genre;

    @ManyToOne
    private Artist artist;

    public Song(String title, Duration duration, LocalDate releaseDate, Genre genre,Artist artist) {
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.artist = artist;
    }
}

