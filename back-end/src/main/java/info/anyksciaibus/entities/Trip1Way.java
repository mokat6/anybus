package info.anyksciaibus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
public class Trip1Way {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Boolean isRouteDirReversed;

    @Enumerated(EnumType.ORDINAL)
    BoundFor boundFor;  //used for filtering routes

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "timeListOrder")
    List<LocalTime> timeList;

    @ManyToOne
    @JoinColumn(name = "route_id")
    Route route;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    Schedule schedule;
    //===================

    public Trip1Way() {
    }

    public Trip1Way(Boolean isRouteDirReversed, BoundFor boundFor, List<LocalTime> timeList, Route route) {
        this.isRouteDirReversed = isRouteDirReversed;
        this.boundFor = boundFor;
        this.timeList = timeList;
        this.route = route;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
