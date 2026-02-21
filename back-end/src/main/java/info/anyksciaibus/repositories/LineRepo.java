package info.anyksciaibus.repositories;

import jakarta.transaction.Transactional;
import info.anyksciaibus.entities.Line;
import info.anyksciaibus.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepo extends JpaRepository<Line, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.line.id = :lineId")
    void deleteSchedulesByLineId(@Param("lineId") Long lineId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Trip1Way t WHERE t.route IN :routes")
    void deleteTrip1WaysByRoutes(@Param("routes") List<Route> routes);
}

