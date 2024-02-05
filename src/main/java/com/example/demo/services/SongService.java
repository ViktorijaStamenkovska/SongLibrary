package com.example.demo.services;

import com.example.demo.models.dtos.ArtistDto;
import com.example.demo.models.entities.Song;
import com.example.demo.models.requests.SongRequest;

import java.time.Duration;
import java.util.List;

public interface SongService {

    List<Song> listAllSongs();

    Song getSongById(int id);

    Song addSong(SongRequest request);

    ArtistDto getArtistById(int id);

    Song getLongestSongByArtistAndGenre(int artistId, int genreId);

    List<Song> getFirst3SongsWithDurationBetween5And10Minutes(Duration minDuration, Duration maxDuration);
}
