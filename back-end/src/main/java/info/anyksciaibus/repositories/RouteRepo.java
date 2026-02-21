package info.anyksciaibus.repositories;

import info.anyksciaibus.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepo extends JpaRepository<Route, Long> {
    List<Route> findByLineId(Long lineId);

}
