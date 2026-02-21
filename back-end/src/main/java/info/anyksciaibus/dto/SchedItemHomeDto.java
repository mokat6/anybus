package info.anyksciaibus.dto;

import info.anyksciaibus.entities.BoundFor;
import info.anyksciaibus.entities.Schedule;

import java.time.LocalTime;
import java.util.List;


public class SchedItemHomeDto {
    Long id;
    String timeDepart;
    BoundFor direction;
    String lineName;
    Boolean isTooLate;




    //===============================================
    public static List<SchedItemHomeDto> scheduleTo2Dto(Schedule schedule){
        LocalTime now = LocalTime.now();

        return schedule.getTrips().stream().map(trip1Way -> {
            SchedItemHomeDto dto = new SchedItemHomeDto();
            dto.setId(trip1Way.getId());

            if (!trip1Way.getTimeList().isEmpty())
                dto.setTimeDepart(trip1Way.getTimeList().getFirst().toString());

            dto.setLineName(trip1Way.getRoute().getLine().getName());

            dto.setTooLate(now.isAfter(trip1Way.getTimeList().getFirst()));

            dto.setDirection(trip1Way.getBoundFor());

            return dto;
        }).toList();
    }




    //=========================================================

    public SchedItemHomeDto() {
    }

    public SchedItemHomeDto(Long id, String timeDepart, BoundFor direction, String lineName, Boolean isTooLate) {
        this.id = id;
        this.timeDepart = timeDepart;
        this.direction = direction;
        this.lineName = lineName;
        this.isTooLate = isTooLate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeDepart() {
        return timeDepart;
    }

    public void setTimeDepart(String timeDepart) {
        this.timeDepart = timeDepart;
    }

    public BoundFor getDirection() {
        return direction;
    }

    public void setDirection(BoundFor direction) {
        this.direction = direction;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Boolean getTooLate() {
        return isTooLate;
    }

    public void setTooLate(Boolean tooLate) {
        isTooLate = tooLate;
    }
}
