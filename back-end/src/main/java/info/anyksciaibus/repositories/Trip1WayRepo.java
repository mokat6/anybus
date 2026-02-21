package info.anyksciaibus.repositories;

import info.anyksciaibus.entities.Line;
import info.anyksciaibus.entities.Trip1Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Trip1WayRepo extends JpaRepository<Trip1Way, Long> {

    List<Trip1Way> findByRouteLine(Line line);

}
