package com.example.demo.controllers;

import com.example.demo.models.entities.Artist;
import com.example.demo.models.requests.ArtistRequest;
import com.example.demo.services.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private static final Logger log = LoggerFactory.getLogger(ArtistController.class);

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        log.info("All artists were requested from the database");

        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable int id) {
        Artist artist = artistService.getArtistByArtistId(id);
        log.info(String.format("Artist with id %d has been requested from database", id));
        return ResponseEntity.ok(artist);
    }

    @PostMapping
    public ResponseEntity<Void> addArtist(@RequestBody @Valid ArtistRequest artistRequest) {
        Artist artist = artistService.addArtist(artistRequest);
        log.info("A request for a artist to be added has been submitted");

        URI location = UriComponentsBuilder
                .fromUriString("/artist/{id}")
                .buildAndExpand(artist.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "/beforeDateAndNationality", params = {"date", "nationality"})
    public ResponseEntity<List<Artist>> findAllByDateOfBirthBeforeAndNationality(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, String nationality) {
        List<Artist> artists = artistService.findAllByDateOfBirthBeforeAndNationality(date, nationality);
        log.info("Artists were requested from the database");

        return ResponseEntity.ok(artists);
    }
}
