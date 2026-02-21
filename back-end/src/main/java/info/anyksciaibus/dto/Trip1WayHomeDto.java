package info.anyksciaibus.dto;

import info.anyksciaibus.entities.BusStop;
import info.anyksciaibus.entities.RouteType;
import info.anyksciaibus.entities.Trip1Way;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip1WayHomeDto {
    Long tripId;
    LocalTime timeDepart;
    String lineName;
    String name;
    RouteType routeType;
    Long lineId;
    Long schedId;



    //==================================
    public static List<Trip1WayHomeDto> tripToDtoList (Trip1Way trip1Way, Long stopId) {

        List<BusStop> stops = trip1Way.getRoute().getStopsArr();
        List<LocalTime> times = trip1Way.getTimeList();
        if (trip1Way.getRouteDirReversed()){
            stops = stops.reversed();
//            times = times.reversed();
        }



        //because some weird routes visit the same stop twice on the same route, very few though
        List<Trip1WayHomeDto> dtoList = new ArrayList<>();

        for (int i = 0; i < stops.size(); i++) {
            if (Objects.equals(stops.get(i).getId(), stopId)) {
                //guard, remove last stop
                if (stops.size() -1 == i) continue;

                Trip1WayHomeDto dto = new Trip1WayHomeDto();
                dto.setTripId(trip1Way.getId());
                dto.setLineName(trip1Way.getRoute().getLine().getName());
                dto.setTimeDepart(times.get(i));
                dto.setRouteType(trip1Way.getRoute().getLine().getRouteType());
                dto.setLineId(trip1Way.getRoute().getLine().getId());
                dto.setSchedId(trip1Way.getSchedule().getId());
                //construct and set name
                RouteType routeType = trip1Way.getRoute().getLine().getRouteType();
                String name;
                if (routeType == RouteType.CITY_BUS)
                    name = getCityBusName(i, stops);
                else
                    name = stops.getLast().getName();

                dto.setName(name);

                dtoList.add(dto);
            }
        }


        return dtoList;
    }

    public static String getCityBusName(int i, List<BusStop> stops){
        String name = "";
        int maxIndex = stops.size() - 1;
        //this method will never receive the last stop, so it's safe
        name = name + stops.get(i+1).getName();

        if (maxIndex >= i + 2)
            name = name + " > " + stops.get(i+2).getName();

        if (maxIndex >= i + 3)
            name = name + " > ...";

        return name;
    }


    //==================================


    public LocalTime getTimeDepart() {
        return timeDepart;
    }

    public void setTimeDepart(LocalTime timeDepart) {
        this.timeDepart = timeDepart;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }


    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getSchedId() {
        return schedId;
    }

    public void setSchedId(Long schedId) {
        this.schedId = schedId;
    }
}
