package com.example.demo.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SongRequest {
    @NotNull(message = "The song title can't be empty")
    private String title;

    @NotNull(message = "The song duration can't be empty")
    private Duration duration;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The release date release date can't be empty")
    private LocalDate releaseDate;

    @Positive(message = "The genre id must be positive")
    @NotNull(message = "The genre id can't be empty")
    private int genre;

    @Positive(message = "The artist id must be positive")
    @NotNull(message = "The artist id can't be empty")
    private int artist;
}
