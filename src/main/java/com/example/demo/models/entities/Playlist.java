package com.example.demo.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private int id;

    private String name;

    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "playlists_songs",
            joinColumns = {@JoinColumn(name = "playlist_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private List<Song> songs;

    public Playlist(String name, Status status, List<Song> songs) {
        this.name = name;
        this.dateOfCreation = LocalDate.now();
        this.status = status;
        this.songs = songs;
    }
}
