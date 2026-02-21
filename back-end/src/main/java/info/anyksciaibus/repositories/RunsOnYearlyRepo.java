package info.anyksciaibus.repositories;

import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;
import info.anyksciaibus.entities.timeconstraints.TypeOfYearlyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunsOnYearlyRepo extends JpaRepository<RunsOnYearly, Long> {

    List<RunsOnYearly> findByTypeOfYearlyRule(TypeOfYearlyRule typeOfYearlyRule);

//    List<Schedule> findByRunsOnYearly(RunsOnYearly runsOnYearly);
//
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Schedule s WHERE s.runsOnYearly = :runsOnYearly")
    boolean existsSchedulesByRunsOnYearly(@Param("runsOnYearly") RunsOnYearly runsOnYearly);

//    @Query("SELECT s FROM Schedule s WHERE s.runsOnYearly = :runsOnYearly")
//    List<Schedule> findSchedulesByRunsOnYearly(@Param("runsOnYearly") RunsOnYearly runsOnYearly);

    @Query("SELECT s.id FROM Schedule s WHERE s.runsOnYearly = :runsOnYearly")
    List<Long> findScheduleIdsByRunsOnYearly(@Param("runsOnYearly") RunsOnYearly runsOnYearly);

}
