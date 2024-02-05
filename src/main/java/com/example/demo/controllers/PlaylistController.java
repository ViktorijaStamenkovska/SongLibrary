package com.example.demo.controllers;

import com.example.demo.models.entities.Playlist;
import com.example.demo.models.requests.PlaylistRequest;
import com.example.demo.services.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private static final Logger log = LoggerFactory.getLogger(PlaylistController.class);

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylist() {
        List<Playlist> playlists = playlistService.listAllPlaylist();
        log.info("All playlists were requested from the database");
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable int id) {
        Playlist playlist = playlistService.getPlaylistByPlaylistId(id);
        log.info(String.format("Playlist with id %d has been requested from database", id));
        return ResponseEntity.ok(playlist);
    }

    @PostMapping
    public ResponseEntity<Void> addPlaylist(@RequestBody @Valid PlaylistRequest playlistRequest) {
        Playlist playlist = playlistService.addPlaylist(playlistRequest);
        log.info("A request for a playlist to be added has been submitted");

        URI location = UriComponentsBuilder
                .fromUriString("/playlist/{id}")
                .buildAndExpand(playlist.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/artist/{artistId}/")
    public ResponseEntity<List<Playlist>> getPlaylistsByArtist(@PathVariable int artistId,
                                                               @RequestParam String artistName,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate artistDateOfBirth) {
        List<Playlist> playlists = playlistService.getPlaylistsByArtist(artistId, artistName, artistDateOfBirth);
        log.info(String.format("Playlist from artist name %s has been requested from database", artistName));
        return ResponseEntity.ok(playlists);

    }

    @GetMapping("/status")
    public ResponseEntity<List<Playlist>> getPlaylistsByStatus(@RequestParam String status) {
        List<Playlist> playlists = playlistService.getPlaylistsByStatus(status);
        log.info(String.format("Playlist with status %s has been requested from database", status));
        return ResponseEntity.ok(playlists);

    }

    @GetMapping("/totalDuration/{id}")
    public ResponseEntity<Duration> getTotalDurationFromPlaylists(@PathVariable int id) {
        Duration duration = playlistService.getTotalDurationFromPlaylists(id);
        log.info(String.format("Total duration for playlist with id %d has been requested from database", id));
        return ResponseEntity.ok(duration);

    }

    @PutMapping("/{playlistId}/add-song/{songId}")
    public ResponseEntity<String> addSongToPlaylist(@PathVariable int playlistId, @PathVariable int songId) {

        Playlist playlist = playlistService.updatePlaylist(playlistId, songId);
        log.info(String.format("Playlist with an id %d was updated", playlistId));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Playlist> deletePlaylist(@PathVariable int id) {

        Playlist playlist = playlistService.deletePlaylist(id);
        log.info(String.format("Playlist with id %d was deleted", id));

        return ResponseEntity.noContent().build();
    }
}
