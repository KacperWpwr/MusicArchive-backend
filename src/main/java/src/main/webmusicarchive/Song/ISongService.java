package src.main.webmusicarchive.Song;

import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;
import src.main.webmusicarchive.Song.request.CreateSongRequest;

public interface ISongService {
    SongDTO addSong(CreateSongRequest request);
    SongListDTO getAllSongs();

    void removeSong(long id);
}
