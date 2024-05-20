package src.main.webmusicarchive.Song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {

    boolean existsByTitle(String title);
}
