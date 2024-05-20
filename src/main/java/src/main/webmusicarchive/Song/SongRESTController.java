package src.main.webmusicarchive.Song;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;
import src.main.webmusicarchive.Song.request.CreateSongRequest;

@RestController
@RequestMapping("/api/v1/songs")
@RequiredArgsConstructor
@CrossOrigin
public class SongRESTController {
    private final ISongService songService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<SongDTO> addSong(@ModelAttribute CreateSongRequest request){
        return new ResponseEntity<>(songService.addSong(request), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<SongListDTO> getAllSongs(){
        return new ResponseEntity<>(
                songService.getAllSongs(),
                HttpStatus.OK
        );
    }
}
