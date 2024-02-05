package com.example.demo.services.impl;

import com.example.demo.exceptions.PlaylistAlreadyExistsException;
import com.example.demo.exceptions.PlaylistNotFoundException;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.Status;
import com.example.demo.models.requests.PlaylistRequest;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.services.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private static final Logger log = LoggerFactory.getLogger(PlaylistServiceImpl.class);

    private final PlaylistRepository playlistRepository;
    private final SongServiceImpl songService;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, SongServiceImpl songService) {
        this.playlistRepository = playlistRepository;
        this.songService = songService;
    }

    @Override
    public List<Playlist> listAllPlaylist() {
        log.info("An attempt to extract all playlists from the database");
        return this.playlistRepository.findAll();
    }

    @Override
    public Playlist getPlaylistByPlaylistId(int id) {
        log.info(String.format("An attempt to extract playlist with id %d from the database", id));

        return playlistRepository.findById(id).orElseThrow(() -> {

            log.error(String.format("Exception caught: %s", "No such playlist was found in the database!"));

            throw new PlaylistNotFoundException("No such playlist was found in the database!");
        });
    }

    @Override
    public Playlist addPlaylist(PlaylistRequest playlistRequest) {
        log.info(String.format("An attempt to add playlist with name '%s' in the database", playlistRequest.getName()));

        this.playlistRepository.findAllByName(playlistRequest.getName()).ifPresent(item -> {
            log.error(String.format("Exception caught: %s", "Playlist already exists!"));

            throw new PlaylistAlreadyExistsException("Playlist already exists!");
        });

        log.info("An attempt to add a new playlist in the database");

        List<Song> songs =
                playlistRequest.getSongs().stream().map(songService::getSongById).collect(Collectors.toList());

        return playlistRepository.save(new Playlist(playlistRequest.getName(),
                playlistRequest.getStatus(), songs));
    }

    @Override
    public List<Playlist> getPlaylistsByArtist(int artistId, String artistName, LocalDate artistDateOfBirth) {
        log.info(String.format("An attempt to extract playlist with artist id %d from the database", artistId));
        return playlistRepository.findPlaylistsByArtistId(artistId);
    }

    @Override
    public List<Playlist> getPlaylistsByStatus(String status) {
        log.info(String.format("An attempt to extract playlist with status %s from the database", status));
        return playlistRepository.findAllByStatus(Status.valueOf(status));
    }

    @Override
    public Duration getTotalDurationFromPlaylists(int id) {
        log.info(String.format("An attempt to extract total duration from playlist with id %d from the database", id));
        Playlist playlist = playlistRepository.findAllById(id).orElseThrow();
        return playlist.getSongs().stream()
                .map(Song::getDuration)
                .reduce(Duration::plus)
                .orElse(Duration.ZERO);
    }

    @Override
    public Playlist updatePlaylist(int playlistId, int songId) {
        log.info(String.format("An attempt to update playlist with id %d", playlistId));
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> {
            log.error(String.format("Exception caught: %s", "No such playlist was found in the database!"));

            throw new PlaylistNotFoundException("No such playlist was found in the database!");
        });

        Song song = songService.getSongById(songId);

        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
        log.info(String.format("Playlist with id %d was updated", playlistId));
        return playlist;
    }

    @Override
    public Playlist deletePlaylist(int id) {
        log.info(String.format("Playlist with id %d was deleted", id));
        return playlistRepository.deleteById(id);
    }
}
