package src.main.webmusicarchive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService implements ISongService{
    private final SongRepository songRepository;
}
