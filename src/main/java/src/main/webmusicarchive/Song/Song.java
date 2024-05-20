package src.main.webmusicarchive;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Song {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private long id;

    private String fileName;

    private String title;
    private String artist;
    private boolean isFavorited;
}
