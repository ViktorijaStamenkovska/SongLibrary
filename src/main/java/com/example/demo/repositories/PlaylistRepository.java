package com.example.demo.repositories;

import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Optional<Playlist> findAllByName(String name);

    Optional<Playlist> findAllById(int id);

    @Query("SELECT DISTINCT p, s.artist.name as artistName, s.artist.dateOfBirth as artistDateOfBirth " +
            "FROM Playlist p JOIN p.songs s " +
            "WHERE s.artist.id = :artistId " +
            "ORDER BY artistName ASC, artistDateOfBirth ASC")
    List<Playlist> findPlaylistsByArtistId(@Param("artistId") int artistId);

    @Query("SELECT p FROM Playlist p WHERE p.status = :status AND SIZE(p.songs) >= 3")
    List<Playlist> findAllByStatus(@Param("status") Status status);

    Playlist deleteById(int id);

}
