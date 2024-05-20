package src.main.webmusicarchive.Song;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import src.main.webmusicarchive.Application.ApplicationException;
import src.main.webmusicarchive.File.FileService;
import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;
import src.main.webmusicarchive.Song.request.CreateSongRequest;

@Service
@RequiredArgsConstructor
public class SongService implements ISongService{
    private final SongRepository songRepository;
    private final FileService fileService;
    private final SongMapper songMapper;


    @Transactional
    public SongDTO addSong(CreateSongRequest request){
        System.out.println(request.artist());
        System.out.println(request.title());
        System.out.println(request.songImage());
        System.out.println(request.songAudio());
        if (request.songAudio() == null){
            throw new ApplicationException("song/0001","Audio track not present", HttpStatus.CONFLICT);
        }
        if (request.songImage() == null){
            throw new ApplicationException("song/0002","Image not present", HttpStatus.CONFLICT);
        }

        String imageFileName = fileService.save(request.songImage());
        String audioFileName = fileService.save(request.songAudio());

        Song song = Song.builder()
                .title(request.title())
                .artist(request.artist())
                .songFileName(audioFileName)
                .imageFileName(imageFileName)
                .build();

        song = songRepository.save(song);

        return new SongDTO(song.getId(),
                song.getTitle(),
                song.getArtist(),
                song.getImageFileName(),
                song.getSongFileName());
    }

    @Override
    public SongListDTO getAllSongs() {
        return songMapper.songListDTO(
                songRepository.findAll()
        );
    }


}
