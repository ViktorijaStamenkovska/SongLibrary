package com.example.demo.models.requests;

import com.example.demo.models.entities.Status;
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
public class PlaylistRequest {
    @Pattern(regexp = "^[A-Z][A-Za-z- ]*$",
            message = "The playlist's name must start with a capital letter and should contain only letters and spaces")
    @NotNull(message = "The playlist's name can't be empty")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The date of creation can't be empty")
    private LocalDate dateOfCreation;

    @Pattern(regexp = "^[A-Z_]+$",
            message = "The status is allowed to contain only capital letters and underscores")
    @NotNull(message = "The status can't be empty")
    private Status status;

    private List<Integer> songs;
}
