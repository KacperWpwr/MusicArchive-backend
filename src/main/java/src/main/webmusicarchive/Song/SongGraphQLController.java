package src.main.webmusicarchive.Song;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SongGraphQLController {
    private final SongService songService;

    @QueryMapping
    public List<Song> songs(){
        return songService.findAllSongs();
    }

    @SchemaMapping
    public String imageFileURI(Song song){
        return songService.getSongAudioFileURI(song);
    }

    @SchemaMapping
    public String audioFileURI(Song song){
        return songService.getSongAudioFileURI(song);
    }

}
