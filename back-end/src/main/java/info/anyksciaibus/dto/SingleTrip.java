package info.anyksciaibus.dto;

import java.util.List;

public class SingleTrip {
    String lineName;
    String routeStart;
    String routeEnd;
    String operator;
    String anykStationPlatform;
    String price;
    String routeType;
    String timeConstraintsDescription;
    List<SingleStop> stops;


    //============================


    public SingleTrip() {
    }

    public SingleTrip(String lineName, String routeStart, String routeEnd,  String operator, String anykStationPlatform, String price, String routeType, String timeConstraintsDescription, List<SingleStop> stops) {
        this.lineName = lineName;
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
        this.operator = operator;
        this.anykStationPlatform = anykStationPlatform;
        this.price = price;
        this.routeType = routeType;
        this.timeConstraintsDescription = timeConstraintsDescription;
        this.stops = stops;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAnykStationPlatform() {
        return anykStationPlatform;
    }

    public void setAnykStationPlatform(String anykStationPlatform) {
        this.anykStationPlatform = anykStationPlatform;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getTimeConstraintsDescription() {
        return timeConstraintsDescription;
    }

    public void setTimeConstraintsDescription(String timeConstraintsDescription) {
        this.timeConstraintsDescription = timeConstraintsDescription;
    }

    public List<SingleStop> getStops() {
        return stops;
    }

    public void setStops(List<SingleStop> stops) {
        this.stops = stops;
    }
}
