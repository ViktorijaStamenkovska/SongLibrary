package com.example.demo.repositories;

import com.example.demo.models.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    Optional<Song> findSongByTitle(String title);

    @Query("SELECT s.title, s.releaseDate FROM Song s WHERE s.artist.id = :id ORDER BY s.title DESC")
    Optional<List<Song>> findAllByArtistId(int id);

    Optional<List<Song>> findAllByArtistIdAndGenreId(int artisId, int genreId);

    @Query("SELECT s FROM Song s WHERE s.duration >= :minDuration AND s.duration <= :maxDuration ORDER BY s.duration DESC")
    List<Song> findFirst3SongsByDurationBetween(@Param("minDuration") Duration minDuration, @Param("maxDuration") Duration maxDuration);
}
