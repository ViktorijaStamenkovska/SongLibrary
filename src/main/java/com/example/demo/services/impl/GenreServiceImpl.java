package com.example.demo.services.impl;

import com.example.demo.exceptions.GenreNotFoundException;
import com.example.demo.models.entities.Genre;
import com.example.demo.repositories.GenreRepository;
import com.example.demo.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private static final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> listAllGenres() {
        log.info("An attempt to extract all genres from the database");
        return this.genreRepository.findAll();
    }

    @Override
    public Genre findGenreById(int id) {
        log.info(String.format("An attempt to extract genre with id %d from the database", id));

        return genreRepository.findGenreById(id).orElseThrow(() -> {

            log.error(String.format("Exception caught: %s", "No such genre was found in the database!"));

            throw new GenreNotFoundException("No such genre was found in the database!");
        });
    }
}
