package info.anyksciaibus.dto;

import info.anyksciaibus.entities.Schedule;
import info.anyksciaibus.entities.Trip1Way;

import java.time.DayOfWeek;
import java.util.List;

//differences from entity - runsOnYearly not object but just a name String, no Line
public class ScheduleBrowseDto {
    Long id;
    List<DayOfWeek> runsOnWeekly;
    String runsOnYearlyStr;
    boolean runsOnPublicHolidays;
    List<Trip1Way> trips;

//--------------------------------------------------
    public static ScheduleBrowseDto scheduleToDto(Schedule schedule){
        ScheduleBrowseDto dto = new ScheduleBrowseDto();
        dto.setId(schedule.getId());
        dto.setTrips(schedule.getTrips());
        dto.setRunsOnYearlyStr(schedule.getRunsOnYearly().getPeriodName());
        dto.setRunsOnWeekly(schedule.getRunsOnWeekly());
        dto.setRunsOnPublicHolidays(schedule.isRunsOnPublicHolidays());

        return dto;
    }

//==================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DayOfWeek> getRunsOnWeekly() {
        return runsOnWeekly;
    }

    public void setRunsOnWeekly(List<DayOfWeek> runsOnWeekly) {
        this.runsOnWeekly = runsOnWeekly;
    }

    public String getRunsOnYearlyStr() {
        return runsOnYearlyStr;
    }

    public void setRunsOnYearlyStr(String runsOnYearlyStr) {
        this.runsOnYearlyStr = runsOnYearlyStr;
    }

    public boolean isRunsOnPublicHolidays() {
        return runsOnPublicHolidays;
    }

    public void setRunsOnPublicHolidays(boolean runsOnPublicHolidays) {
        this.runsOnPublicHolidays = runsOnPublicHolidays;
    }

    public List<Trip1Way> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip1Way> trips) {
        this.trips = trips;
    }
}