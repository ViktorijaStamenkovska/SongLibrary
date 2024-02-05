package com.example.demo.services;

import com.example.demo.models.entities.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> listAllGenres();

    Genre findGenreById(int id);
}
