package com.example.demo.services.impl;

import com.example.demo.exceptions.ArtistAlreadyExistsException;
import com.example.demo.exceptions.ArtistNotFoundException;
import com.example.demo.exceptions.DateNotValidException;
import com.example.demo.models.entities.Artist;
import com.example.demo.models.requests.ArtistRequest;
import com.example.demo.repositories.ArtistRepository;
import com.example.demo.services.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final static Logger log = LoggerFactory.getLogger(ArtistServiceImpl.class);

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> getAllArtists() {
        log.info("An attempt to extract all artists from the database");
        return artistRepository.findAll();
    }


    public Artist getArtistByArtistId(int id) {
        log.info(String.format("An attempt to extract category with id %d from the database", id));

        return artistRepository.findById(id).orElseThrow(() -> {

            log.error(String.format("Exception caught: %s", "No such artist was found in the database!"));

            throw new ArtistNotFoundException("No such artist was found in the database!");
        });
    }

    @Override
    public Artist addArtist(ArtistRequest artistRequest) {
        log.info("An attempt to save a artist in the database");
        artistRepository.findArtistsByName(artistRequest.getName()).ifPresent(category -> {
            log.error(String.format("Exception caught: %s", "Artist already exists!"));

            throw new ArtistAlreadyExistsException("Artist already exists!");
        });
        if (isDateNotValid(artistRequest.getDateOfBirth())) {

            log.error(String.format("Exception caught: %s", "Date not valid."));

            throw new DateNotValidException("Date not valid.");
        }

        return artistRepository.save(new Artist(artistRequest.getName(),
                artistRequest.getArtisticName(),
                artistRequest.getDateOfBirth(),
                artistRequest.getNationality()
        ));

    }

    public boolean isDateNotValid(LocalDate dateOfBirth) {
        return dateOfBirth.isAfter(LocalDate.now());
    }

    @Override
    public List<Artist> findAllByDateOfBirthBeforeAndNationality(LocalDate date, String nationality) {
        log.info("An attempt to extract all artists from the database born before 1999 and are Macedonian");

        return artistRepository.findAllByDateOfBirthBeforeAndNationality(date, nationality).orElseThrow(() -> {

            log.error(String.format("Exception caught: %s", "No such artist was found in the database!"));

            throw new ArtistNotFoundException("No such artist was found in the database!");
        });
    }

}
