package com.example.demo.repositories;

import com.example.demo.models.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    Optional<Artist> findArtistsByName(String name);

    @Query("SELECT a.name, a.artisticName FROM Artist a WHERE a.dateOfBirth < :date AND a.nationality = :nat")
    Optional<List<Artist>> findAllByDateOfBirthBeforeAndNationality(LocalDate date, String nat);

}
