package info.anyksciaibus.dto;

import info.anyksciaibus.entities.BusStop;

import java.util.List;

public class RouteDto {

    private Long id;
    String routeNotes;
    List<BusStop> stopsArr;
    List<Integer> distanceMetersArr;
    Long lineId;

    // =====================================


    public RouteDto() {
    }

    public RouteDto(Long id, String routeNotes, List<BusStop> stopsArr, List<Integer> distanceMetersArr, Long lineId) {
        this.id = id;
        this.routeNotes = routeNotes;
        this.stopsArr = stopsArr;
        this.distanceMetersArr = distanceMetersArr;
        this.lineId = lineId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getRouteNotes() {
        return routeNotes;
    }

    public void setRouteNotes(String routeNotes) {
        this.routeNotes = routeNotes;
    }

    public List<BusStop> getStopsArr() {
        return stopsArr;
    }

    public void setStopsArr(List<BusStop> stopsArr) {
        this.stopsArr = stopsArr;
    }

    public List<Integer> getDistanceMetersArr() {
        return distanceMetersArr;
    }

    public void setDistanceMetersArr(List<Integer> distanceMetersArr) {
        this.distanceMetersArr = distanceMetersArr;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }
}