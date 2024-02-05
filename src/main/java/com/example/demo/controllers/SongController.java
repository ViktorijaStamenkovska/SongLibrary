package com.example.demo.controllers;

import com.example.demo.models.dtos.ArtistDto;
import com.example.demo.models.entities.Song;
import com.example.demo.models.requests.SongRequest;
import com.example.demo.services.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.listAllSongs();
        log.info("All songs were requested from the database");
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable int id) {
        Song song = songService.getSongById(id);
        log.info(String.format("Song with id %d has been requested from database", id));
        return ResponseEntity.ok(song);
    }


    @PostMapping
    public ResponseEntity<Void> addSong(@RequestBody @Valid SongRequest request) {
        Song song = songService.addSong(request);
        log.info("A request for a song to be added has been submitted");

        URI location = UriComponentsBuilder
                .fromUriString("/songs/{id}")
                .buildAndExpand(song.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/sorted/{artistId}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable int artistId) {
        ArtistDto artist = songService.getArtistById(artistId);
        log.info(String.format("Artist with id %d and list of his songs has been requested from database", artistId));
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/artists/{artistId}/genres/{genreId}/longest-song")
    public ResponseEntity<Song> getLongestSongByArtistAndGenre(
            @PathVariable int artistId,
            @PathVariable int genreId) {
        Song song = songService.getLongestSongByArtistAndGenre(artistId, genreId);
        log.info(String.format("Found the longest song from artist with id '%d' ang genre id %d", artistId, genreId));
        return ResponseEntity.ok(song);
    }

    @GetMapping("/firstSongs")
    public ResponseEntity<List<Song>> getFirst3SongsWithDurationBetween5And10Minutes(@RequestParam Duration minDuration, @RequestParam Duration maxDuration) {
        List<Song> songs = songService.getFirst3SongsWithDurationBetween5And10Minutes(minDuration, maxDuration);
        return ResponseEntity.ok(songs);
    }
}
