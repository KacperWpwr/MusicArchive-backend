package src.main.webmusicarchive.Song;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import src.main.webmusicarchive.Application.ApplicationException;
import src.main.webmusicarchive.File.FileService;
import src.main.webmusicarchive.Song.dto.SongDTO;
import src.main.webmusicarchive.Song.dto.SongListDTO;
import src.main.webmusicarchive.Song.request.CreateSongRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService implements ISongService{
    private final SongRepository songRepository;
    private final FileService fileService;
    private final SongMapper songMapper;
    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);


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
        Song song = Song.builder()
                .title(request.title())
                .artist(request.artist())
                .build();

        song = songRepository.save(song);
        String imageFileName = fileService.save(request.songImage(), "song-"+song.getId()+"-image");
        String audioFileName = fileService.save(request.songAudio(),"song-"+song.getId()+"-audio");

        song.setSongFileName(audioFileName);
        song.setImageFileName(imageFileName);
        song = songRepository.save(song);
        return songMapper.songDTO(song);
    }

    @Override
    public SongListDTO getAllSongs() {
        return songMapper.songListDTO(
                songRepository.findAll()
        );
    }

    @Override
    public void removeSong(long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(()->new ApplicationException("song/0003","Song not found",HttpStatus.NOT_FOUND));

        LOG.info(song.getSongFileName());
        fileService.remove(song.getImageFileName());
        fileService.remove(song.getSongFileName());

        songRepository.delete(song);
    }

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public String getSongImageFileURI(Song song) {
        return fileService.convertFileNameToBucketURI(song.getImageFileName());
    }

    @Override
    public String getSongAudioFileURI(Song song) {
        return fileService.convertFileNameToBucketURI(song.getSongFileName());
    }


}
