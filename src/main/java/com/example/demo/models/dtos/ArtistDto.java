package com.example.demo.models.dtos;

import com.example.demo.models.entities.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtistDto {
    private int id;
    private String name;
    private String artisticName;
    private LocalDate dateOfBirth;
    private String nationality;
    private List<Song> songs;

}
