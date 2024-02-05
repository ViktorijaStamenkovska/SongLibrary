package com.example.demo.services;

import com.example.demo.models.entities.Artist;
import com.example.demo.models.requests.ArtistRequest;

import java.time.LocalDate;
import java.util.List;

public interface ArtistService {
    List<Artist> getAllArtists();

    Artist getArtistByArtistId(int id);

    List<Artist> findAllByDateOfBirthBeforeAndNationality(LocalDate date, String nationality);

    Artist addArtist(ArtistRequest artistRequest);

}
