package src.main.webmusicarchive;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/songs")
@RequiredArgsConstructor
public class SongRESTController {
    private final ISongService songService;
}
