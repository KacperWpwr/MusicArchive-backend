package src.main.webmusicarchive.File;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {
    private static final String MESSAGE_1 = "Uploaded the file successfully";
    private static final String FILE_NAME = "fileName";


    private final FileService fileService;


    @GetMapping(value = "/image", produces = "image/png")
    public ResponseEntity<Object> findByName(@RequestParam String fileName) {
        return ResponseEntity
                .ok()
                .body(new InputStreamResource(fileService.findByName(fileName)));

    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile multipartFile) {
        fileService.save(multipartFile);
        return new ResponseEntity<>(MESSAGE_1, HttpStatus.OK);
    }

    @GetMapping(value = "/song", produces = "audio/mp3")
    public ResponseEntity<Object> getSong(@RequestParam String fileName){
        return ResponseEntity
                .ok()
                .body(new InputStreamResource(fileService.findByName(fileName)));
    }

}
