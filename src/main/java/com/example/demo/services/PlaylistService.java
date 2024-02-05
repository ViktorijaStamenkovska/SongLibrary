package com.example.demo.services;

import com.example.demo.models.entities.Playlist;
import com.example.demo.models.requests.PlaylistRequest;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface PlaylistService {

    List<Playlist> listAllPlaylist();

    Playlist getPlaylistByPlaylistId(int id);

    Playlist addPlaylist(PlaylistRequest playlistRequest);

    List<Playlist> getPlaylistsByArtist(int artistId, String artistName, LocalDate artistDateOfBirth);

    List<Playlist> getPlaylistsByStatus(String status);

    Duration getTotalDurationFromPlaylists(int id);

    Playlist updatePlaylist(int playlistRequest, int id);

    Playlist deletePlaylist(int id);
}
