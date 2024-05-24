package src.main.webmusicarchive.Song;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import src.main.webmusicarchive.File.FileService;
import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SongMapper {
    private final FileService fileService;

    public SongDTO songDTO(Song song){
        return new SongDTO(song.getId(),
                song.getTitle(),
                song.getArtist(),
                fileService.convertFileNameToBucketURI(song.getImageFileName()),
                fileService.convertFileNameToBucketURI(song.getSongFileName()));
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
