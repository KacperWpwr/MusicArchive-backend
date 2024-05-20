package src.main.webmusicarchive.Song.request;


import org.springframework.web.multipart.MultipartFile;

public record CreateSongRequest(String title, String artist, MultipartFile songImage, MultipartFile songAudio) {
}
