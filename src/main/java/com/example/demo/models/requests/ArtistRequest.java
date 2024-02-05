package com.example.demo.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtistRequest {
    @Pattern(regexp = "^[A-Z][A-Za-z- ]*$",
            message = "The artist's name must start with a capital letter and should contain only letters and spaces")
    @NotNull(message = "The artist's name can't be empty")
    private String name;

    @Pattern(regexp = "^[A-Z][A-Za-z- ]*$",
            message = "The artisticName's name must start with a capital letter and should contain only letters and spaces")
    @NotNull(message = "The artisticName's name can't be empty")
    private String artisticName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The date of birth date can't be empty")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^[A-Z][A-Za-z- ]*$",
            message = "The nationality's name must start with a capital letter and should contain only letters and spaces")
    @NotNull(message = "The nationality's name can't be empty")
    private String nationality;

    private List<Integer> songs;
}
