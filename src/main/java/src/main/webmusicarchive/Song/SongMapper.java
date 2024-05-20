package src.main.webmusicarchive.Song;

import org.springframework.stereotype.Component;
import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;

import java.util.List;

@Component
public class SongMapper {

    public SongDTO songDTO(Song song){
        return new SongDTO(song.getId(),
                song.getTitle(),
                song.getArtist(),
                song.getImageFileName(),
                song.getSongFileName());
    };

    public SongListDTO songListDTO(List<Song> songs){
        return new SongListDTO(
                songs.
                        stream()
                        .map(this::songDTO)
                        .toList()
        );
    }
}
