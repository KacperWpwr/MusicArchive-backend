package src.main.webmusicarchive.Song;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private long id;

    private String imageFileName;
    private String songFileName;

    private String title;
    private String artist;
    private boolean isFavorited;
}
