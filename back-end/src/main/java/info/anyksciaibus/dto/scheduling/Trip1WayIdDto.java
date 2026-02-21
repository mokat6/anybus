package info.anyksciaibus.dto.scheduling;

import com.fasterxml.jackson.annotation.*;
import info.anyksciaibus.entities.*;

import java.time.LocalTime;
import java.util.List;

public class Trip1WayIdDto {
    Long id;

    Boolean isRouteDirReversed;
    BoundFor boundFor;  //used for filtering routes
    List<LocalTime> timeList;

    //-------------------------serialize annotations, @JsonSetter for deserializing id to obj
//    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("routeId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    Route route;

    @JsonSetter
    public void setRouteId(Long id){
        this.route = new Route(id);
    }
    //===================

    public static Trip1WayIdDto tripToDto(Trip1Way trip1way){
        Trip1WayIdDto dto = new Trip1WayIdDto();
        dto.setBoundFor(trip1way.getBoundFor());
        dto.setId(trip1way.getId());
        dto.setRoute(trip1way.getRoute());
        dto.setRouteDirReversed(trip1way.getRouteDirReversed());
        dto.setTimeList(trip1way.getTimeList());
        return dto;
    }

    public static Trip1Way dtoToTrip1Way(Trip1WayIdDto dto, Schedule schedule) {
        Trip1Way trip = new Trip1Way();
        trip.setId(dto.getId());
        trip.setRouteDirReversed(dto.getRouteDirReversed());
        trip.setBoundFor(dto.getBoundFor());
        trip.setTimeList(dto.getTimeList());
        trip.setSchedule(schedule);
        trip.setRoute(dto.getRoute());
        return trip;
    }

    //===================


    public Trip1WayIdDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRouteDirReversed() {
        return isRouteDirReversed;
    }

    public void setRouteDirReversed(Boolean routeDirReversed) {
        isRouteDirReversed = routeDirReversed;
    }

    public BoundFor getBoundFor() {
        return boundFor;
    }

    public void setBoundFor(BoundFor boundFor) {
        this.boundFor = boundFor;
    }

    public List<LocalTime> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<LocalTime> timeList) {
        this.timeList = timeList;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
