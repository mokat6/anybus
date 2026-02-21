package info.anyksciaibus.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.anyksciaibus.entities.Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleByYearlyRuleDto {
    Long id;
    List<Map<String, String>> tripsFirstEntryAndDir;
    List<DayOfWeek> runsOnWeekly;   //which days of the week

    @JsonIgnore
    LineInfo lineInfo;

    //------------------------------------------

    public static ScheduleByYearlyRuleDto ScheduleToDto (Schedule schedule){
        ScheduleByYearlyRuleDto dto = new ScheduleByYearlyRuleDto();
        dto.setId(schedule.getId());
        dto.setRunsOnWeekly(schedule.getRunsOnWeekly());
        dto.setLineInfo(LineInfo.LineToDto(schedule.getLine()));


        List<Map<String, String>> tripTimeDir =
                schedule.getTrips().stream().map(trip -> {
            Map<String, String> timeAndDir = new HashMap<>();

            String time = "";
            LocalTime firstTrip = trip.getTimeList().getFirst();
            if (firstTrip != null)
                  time = firstTrip.getHour() + ":" +firstTrip.getMinute();

            String dir = trip.getBoundFor().getDescription();

            timeAndDir.put("time", time);
            timeAndDir.put("dir", dir);

            return timeAndDir;
        }).toList();

        dto.setTripsFirstEntryAndDir(tripTimeDir);

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Map<String, String>> getTripsFirstEntryAndDir() {
        return tripsFirstEntryAndDir;
    }

    public void setTripsFirstEntryAndDir(List<Map<String, String>> tripsFirstEntryAndDir) {
        this.tripsFirstEntryAndDir = tripsFirstEntryAndDir;
    }

    public List<DayOfWeek> getRunsOnWeekly() {
        return runsOnWeekly;
    }

    public void setRunsOnWeekly(List<DayOfWeek> runsOnWeekly) {
        this.runsOnWeekly = runsOnWeekly;
    }

    public LineInfo getLineInfo() {
        return lineInfo;
    }

    public void setLineInfo(LineInfo lineInfo) {
        this.lineInfo = lineInfo;
    }
}

