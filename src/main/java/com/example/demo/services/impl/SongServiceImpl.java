package com.example.demo.services.impl;

import com.example.demo.exceptions.DateNotValidException;
import com.example.demo.exceptions.SongAlreadyExistsException;
import com.example.demo.exceptions.SongNotFoundException;
import com.example.demo.models.dtos.ArtistDto;
import com.example.demo.models.entities.Artist;
import com.example.demo.models.entities.Genre;
import com.example.demo.models.entities.Song;
import com.example.demo.models.requests.SongRequest;
import com.example.demo.repositories.SongRepository;
import com.example.demo.services.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private final SongRepository songRepository;
    private final GenreServiceImpl genreService;
    private final ArtistServiceImpl artistService;


    @Autowired
    public SongServiceImpl(SongRepository songRepository, GenreServiceImpl genreService, ArtistServiceImpl artistService) {
        this.songRepository = songRepository;
        this.genreService = genreService;
        this.artistService = artistService;
    }

    @Override
    public List<Song> listAllSongs() {
        log.info("An attempt to extract all songs from the database");
        return this.songRepository.findAll();
    }

    @Override
    public Song getSongById(int id) {
        log.info(String.format("An attempt to extract song with id %d from the database", id));

        return songRepository.findById(id).orElseThrow(() -> {

            log.error(String.format("Exception caught: %s", "No such song was found in the database!"));

            throw new SongNotFoundException("No such song was found in the database!");
        });
    }

    @Override
    public Song addSong(SongRequest songRequest) {
        log.info(String.format("An attempt to add song with title '%s' in the database", songRequest.getTitle()));

        this.songRepository.findSongByTitle(songRequest.getTitle()).ifPresent(item -> {
            log.error(String.format("Exception caught: %s", "Song already exists!"));

            throw new SongAlreadyExistsException("Song already exists!");
        });
        if (isDateNotValid(songRequest.getReleaseDate())) {

            log.error(String.format("Exception caught: %s", "Date not valid."));

            throw new DateNotValidException("Date not valid.");
        }
        Genre genre = genreService.findGenreById(songRequest.getGenre());
        Artist artist = artistService.getArtistByArtistId(songRequest.getArtist());
        log.info("An attempt to add a new song in the database");

        return songRepository.save(new Song(songRequest.getTitle(), songRequest.getDuration(),
                songRequest.getReleaseDate(), genre, artist));
    }

    public boolean isDateNotValid(LocalDate releaseDate) {
        return releaseDate.isAfter(LocalDate.now());
    }


    @Override
    public ArtistDto getArtistById(int id) {
        Artist artist = artistService.getArtistByArtistId(id);
        List<Song> songs = songRepository.findAllByArtistId(id).orElseThrow();
        log.info(String.format("An attempt to get artist with id %d and list of his songs", id));
        return new ArtistDto(artist.getId(), artist.getName(), artist.getArtisticName(), artist.getDateOfBirth(),
                artist.getNationality(), songs);
    }

    @Override
    public Song getLongestSongByArtistAndGenre(int artistId, int genreId) {
        log.info(String.format("An attempt to found the longest song from artist with id '%d' ang genre id %d", artistId, genreId));
        List<Song> songs = songRepository.findAllByArtistIdAndGenreId(artistId, genreId).orElseThrow();
        return songs.stream().max(Comparator.comparing(Song::getDuration)).orElseThrow();
    }

    @Override
    public List<Song> getFirst3SongsWithDurationBetween5And10Minutes(Duration minDuration, Duration maxDuration) {
        List<Song> songs = songRepository.findFirst3SongsByDurationBetween(minDuration, maxDuration);
        return songs.stream().limit(3).collect(Collectors.toList());
    }
}
