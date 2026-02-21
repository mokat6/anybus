package info.anyksciaibus.repositories;

import jakarta.transaction.Transactional;
import info.anyksciaibus.entities.BoundFor;
import info.anyksciaibus.entities.Route;
import info.anyksciaibus.entities.Schedule;
import info.anyksciaibus.entities.Trip1Way;
import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s " +
            "WHERE :dayOfWeek MEMBER OF s.runsOnWeekly " +
            "AND s.runsOnPublicHolidays = :isPublicHoliday " +
            "AND s.runsOnYearly IN :runsOnYearlyList")
    List<Schedule> findSchedulesByDayOfWeekAndPublicHolidayAndRunsOnYearly(DayOfWeek dayOfWeek, boolean isPublicHoliday, List<RunsOnYearly> runsOnYearlyList);

//    @Query("SELECT s FROM Schedule s " +
//            "WHERE UPPER(s.boundFor) = UPPER(:dayOfWeek) " +
//            "AND s.runsOnPublicHolidays = :isPublicHoliday " +
//            "AND s.runsOnYearly IN :runsOnYearlyList")
//    List<Schedule> findSchedulesByDayOfWeekAndPublicHolidayAndRunsOnYearlyList(DayOfWeek dayOfWeek, boolean isPublicHoliday, List<RunsOnYearly> runsOnYearlyList);

    //TODO this not working anymore, and not needed
//    void deleteByRoute(Route route);

//    List<Schedule> findByRoute_Line_Id(Long lineId);
    List<Schedule> findByLineId(Long lineId);


    @Transactional
    void deleteByTripsRoute(Route route);

//    List<Schedule> findByLine(Line line);
//    List<Schedule> findByRoute(Route route);

//    @Query("SELECT s.id FROM Schedule s WHERE s.route = :route")
//    List<Long> findScheduleIdsByRoute(Route route);

//    @Query("SELECT DISTINCT t.route.id FROM Schedule s JOIN s.trips t WHERE s.route = :route")
//    List<Long> findScheduleRouteIdsByRoute(Route route);

    @Query("SELECT DISTINCT s.id FROM Schedule s JOIN s.trips t WHERE t.route = :route")
    List<Long> findScheduleIdsByRoute(Route route);

    @Query("SELECT CONCAT(l.name, ' - ', l.routeStart, ' - ', l.routeEnd) FROM Line l WHERE l.id = :lineId")
    Optional<String> getLineTitleByLineId(Long lineId);

    List<Schedule> findByRunsOnYearlyId(Long runsOnYearlyId);


    //------------------


    @Query("SELECT t FROM Schedule s JOIN s.trips t "
            + "WHERE (:runsOnPublicHolidays IS NULL OR s.runsOnPublicHolidays = :runsOnPublicHolidays) "
            + "AND s.runsOnYearly IN :runsOnYearlyList "
            + "AND :dayOfWeek MEMBER OF s.runsOnWeekly "
            + "AND (t.boundFor = :boundFor OR t.boundFor = ONE_WAY) "
            + "AND EXISTS (SELECT bs FROM Route r JOIN r.stopsArr bs WHERE r = t.route AND bs.id = :busStopId) ")
    List<Trip1Way> findTripsByConditions(@Param("runsOnYearlyList") List<RunsOnYearly> runsOnYearlyList,
                                         @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                         @Param("boundFor") BoundFor boundFor,
                                         @Param("busStopId") Long busStopId,
                                         @Param("runsOnPublicHolidays") Boolean runsOnPublicHolidays);

    @Query("SELECT t FROM Schedule s JOIN s.trips t "
            + "WHERE (:runsOnPublicHolidays IS NULL OR s.runsOnPublicHolidays = :runsOnPublicHolidays) "
            + "AND s.runsOnYearly IN :runsOnYearlyList "
            + "AND :dayOfWeek MEMBER OF s.runsOnWeekly "
            + "AND (t.boundFor = :boundFor OR t.boundFor = ONE_WAY) "
            + "AND EXISTS (SELECT bs FROM Route r JOIN r.stopsArr bs WHERE r = t.route AND bs.id = :busStopId) "
            + "AND t.route.line.routeType != CITY_BUS") // Exclude schedules where Trip1Way's route's Line's routeType is CITY_BUS
    List<Trip1Way> findTripsByConditionsNotCityBus(@Param("runsOnYearlyList") List<RunsOnYearly> runsOnYearlyList,
                                         @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                         @Param("boundFor") BoundFor boundFor,
                                         @Param("busStopId") Long busStopId,
                                         @Param("runsOnPublicHolidays") Boolean runsOnPublicHolidays);

    @Query("SELECT t FROM Schedule s JOIN s.trips t "
            + "WHERE (:runsOnPublicHolidays IS NULL OR s.runsOnPublicHolidays = :runsOnPublicHolidays) "
            + "AND s.runsOnYearly IN :runsOnYearlyList "
            + "AND :dayOfWeek MEMBER OF s.runsOnWeekly "
            + "AND EXISTS (SELECT bs FROM Route r JOIN r.stopsArr bs WHERE r = t.route AND bs.id = :busStopId) "
            + "AND t.route.line.routeType = CITY_BUS") // Exclude schedules where Trip1Way's route's Line's routeType is CITY_BUS
    List<Trip1Way> findTripsCityBus(@Param("runsOnYearlyList") List<RunsOnYearly> runsOnYearlyList,
                                         @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                         @Param("busStopId") Long busStopId,
                                         @Param("runsOnPublicHolidays") Boolean runsOnPublicHolidays);


    @Query("SELECT t FROM Schedule s JOIN s.trips t "
            + "WHERE (:runsOnPublicHolidays IS NULL OR s.runsOnPublicHolidays = :runsOnPublicHolidays) "
            + "AND s.runsOnYearly IN :runsOnYearlyList "
            + "AND :dayOfWeek MEMBER OF s.runsOnWeekly "
            + "AND (t.boundFor = :boundFor OR t.boundFor = ONE_WAY) "
            + "AND EXISTS (SELECT bs FROM Route r JOIN r.stopsArr bs WHERE r = t.route AND bs.id = :busStopId) "
            + "AND t.boundFor != TWO_WAYS_CITY_BOUND "
            + "AND EXISTS (SELECT bs FROM Route r JOIN r.stopsArr bs WHERE r = t.route AND bs.id = :qbstopto)")
    List<Trip1Way> findTripsByConditionsToAndFrom(@Param("runsOnYearlyList") List<RunsOnYearly> runsOnYearlyList,
                                         @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                         @Param("boundFor") BoundFor boundFor,
                                         @Param("busStopId") Long busStopId,
                                         @Param("runsOnPublicHolidays") Boolean runsOnPublicHolidays,
                                         @Param("qbstopto") Long qbstopto); // New parameter: qbstopto




    @Query("SELECT s FROM Schedule s JOIN s.trips t WHERE t.id = :tripId")
    Optional<Schedule> findByTripId(@Param("tripId") Long tripId);

}
