package com.example.demo.controllers;

import com.example.demo.models.entities.Genre;
import com.example.demo.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private static final Logger log = LoggerFactory.getLogger(GenreController.class);

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenre() {
        List<Genre> genres = genreService.listAllGenres();
        log.info("All genres were requested from the database");
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        Genre genre = genreService.findGenreById(id);
        log.info(String.format("Genre with id %d has been requested from database", id));
        return ResponseEntity.ok(genre);
    }
}
