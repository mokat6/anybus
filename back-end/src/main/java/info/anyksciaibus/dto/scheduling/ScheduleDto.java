package info.anyksciaibus.dto.scheduling;

import com.fasterxml.jackson.annotation.*;
import info.anyksciaibus.entities.Line;
import info.anyksciaibus.entities.Schedule;
import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;

public class ScheduleDto {
    Long id;

    List<Trip1WayIdDto> trips;

    String timeConstraintsDescription;
    List<DayOfWeek> runsOnWeekly;   //which days of the week


    //-------------------------serialize annotations, @JsonSetter for deserializing id to obj
    @JsonProperty("runsOnYearlyId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    RunsOnYearly runsOnYearly;

    @JsonSetter
    public void setRunsOnYearlyId(Long id){
        this.runsOnYearly = new RunsOnYearly(id);
    }
    //---------------------------

    boolean runsOnPublicHolidays; //true = runs on public holidays

    //-------------------------serialize annotations, @JsonSetter for deserializing id to obj
    @JsonProperty("lineId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Line line;

    @JsonSetter
    public void setLineId(Long id){
        this.line = new Line(id);
    }
    //==================

    public static ScheduleDto scheduleToDto(Schedule schedule){
        ScheduleDto dto = new ScheduleDto();
        dto.setId(schedule.getId());
        dto.setTimeConstraintsDescription(schedule.getTimeConstraintsDescription());
        dto.setRunsOnPublicHolidays(schedule.isRunsOnPublicHolidays());

        dto.setRunsOnYearly(schedule.getRunsOnYearly());
//        if (schedule.getRunsOnYearly() != null)
//            dto.setRunsOnYearlyId(schedule.getRunsOnYearly().getId());

        if (schedule.getRunsOnWeekly() == null)
            dto.setRunsOnWeekly(Collections.emptyList());
        else
            dto.setRunsOnWeekly(schedule.getRunsOnWeekly());

        if (schedule.getTrips() == null)
            dto.setTrips(Collections.emptyList());
        else
            dto.setTrips(schedule.getTrips().stream().map(Trip1WayIdDto::tripToDto).toList());

        dto.setLine(schedule.getLine());

        return dto;
    }

    //==================

    public static Schedule dtoToSchedule(ScheduleDto dto){
        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setTrips(dto.getTrips().stream().map(trip -> Trip1WayIdDto.dtoToTrip1Way(trip, schedule)).toList());
        schedule.setLine(dto.getLine());
        schedule.setRunsOnYearly(dto.getRunsOnYearly());
        schedule.setRunsOnWeekly(dto.getRunsOnWeekly());
        schedule.setRunsOnPublicHolidays(dto.getRunsOnPublicHolidays());
        schedule.setTimeConstraintsDescription(dto.getTimeConstraintsDescription());


        return schedule;
    }

    //==================
    public ScheduleDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Trip1WayIdDto> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip1WayIdDto> trips) {
        this.trips = trips;
    }

    public String getTimeConstraintsDescription() {
        return timeConstraintsDescription;
    }

    public void setTimeConstraintsDescription(String timeConstraintsDescription) {
        this.timeConstraintsDescription = timeConstraintsDescription;
    }






    public List<DayOfWeek> getRunsOnWeekly() {
        return runsOnWeekly;
    }

    public void setRunsOnWeekly(List<DayOfWeek> runsOnWeekly) {
        this.runsOnWeekly = runsOnWeekly;
    }

    public RunsOnYearly getRunsOnYearly() {
        return runsOnYearly;
    }

    public void setRunsOnYearly(RunsOnYearly runsOnYearly) {
        this.runsOnYearly = runsOnYearly;
    }

    public boolean getRunsOnPublicHolidays() {
        return runsOnPublicHolidays;
    }

    public void setRunsOnPublicHolidays(boolean runsOnPublicHolidays) {
        this.runsOnPublicHolidays = runsOnPublicHolidays;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }


}

