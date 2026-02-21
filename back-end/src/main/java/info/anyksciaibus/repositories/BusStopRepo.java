package info.anyksciaibus.repositories;

import info.anyksciaibus.dto.BusStopsDto;
import info.anyksciaibus.dto.LineIdNameDto;
import info.anyksciaibus.entities.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStopRepo extends JpaRepository<BusStop, Long> {
    //when searchTerm == ""
    List<BusStop> findByDefaultOptionTrue();

    //used for searchTerm > 2,     Ordered by first - starts as search term, then alphabetically
    @Query("SELECT b FROM BusStop b WHERE LOWER(b.name) LIKE LOWER(concat('%', ?1, '%')) ORDER BY CASE WHEN LOWER(b.name) LIKE LOWER(concat(?1, '%')) THEN 0 ELSE 1 END, b.name")
    List<BusStop> findTop10ByNameContainingIgnoreCase(String searchTerm);

    //at the start of a word (not string), used when searchTerm.length < 3. Ordered by first - starts as search term, then alphabetically
    @Query("SELECT b FROM BusStop b WHERE (LOWER(b.name) LIKE LOWER(concat(?1, '%')) OR LOWER(b.name) LIKE LOWER(concat('% ', ?1, '%'))) ORDER BY CASE WHEN LOWER(b.name) LIKE LOWER(concat(?1, '%')) THEN 0 ELSE 1 END, b.name")
    List<BusStop> findTop10ByNameStartingWithIgnoreCase(String searchTerm);

    List<BusStop> findByNameContaining(String searchQuery);

    //get all stops, only need id and name, id rename to value, name rename to label
    @Query("SELECT new info.anyksciaibus.dto.BusStopsDto(bs.id AS value, bs.name AS label) FROM BusStop bs")
    List<BusStopsDto> findAllBusStops();

//    @Query("SELECT DISTINCT r.line.id FROM Route r JOIN r.stopsArr s WHERE s.id = :busStopId")
//    List<Long> findLineIdsByBusStopId(@Param("busStopId") Long busStopId);

    //DELETE THIS
    @Query("SELECT DISTINCT r.line.id FROM Route r JOIN r.stopsArr s WHERE s = :busStop")
    List<Long> findLineIdsByBusStop(@Param("busStop") BusStop busStop);

    @Query("SELECT DISTINCT NEW info.anyksciaibus.dto.LineIdNameDto(r.line.id, r.line.name) FROM Route r JOIN r.stopsArr s WHERE s = :busStop")
    List<LineIdNameDto> findLineIdsAndNamesByBusStop(@Param("busStop") BusStop busStop);

//    @Query("SELECT DISTINCT r.line FROM Route r JOIN r.stopsArr s WHERE s = :busStop")
//    List<Line> findLinesByBusStop(@Param("busStop") BusStop busStop);


    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Route r JOIN r.stopsArr s WHERE s.id = :busStopId")
    boolean existsRoutesForBusStop(@Param("busStopId") Long busStopId);

}
