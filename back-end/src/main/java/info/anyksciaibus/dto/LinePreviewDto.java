package info.anyksciaibus.dto;

import info.anyksciaibus.entities.BusStop;
import info.anyksciaibus.entities.Line;
import info.anyksciaibus.entities.Route;

import java.util.ArrayList;
import java.util.List;

public class LinePreviewDto {
    Long id;
    String name;
    String routeStart;  //which city, not which station
    String routeEnd;    //which city, not which station
    List<String> mainStops;
    List<String> extraStops;

    //=============================

    public LinePreviewDto() {
    }

    public LinePreviewDto(Long id, String name, String routeStart, String routeEnd, List<String> mainStops, List<String> extraStops) {
        this.id = id;
        this.name = name;
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
        this.mainStops = mainStops;
        this.extraStops = extraStops;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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



    public List<String> getMainStops() {
        return mainStops;
    }

    public void setMainStops(List<String> mainStops) {
        this.mainStops = mainStops;
    }

    public List<String> getExtraStops() {
        return extraStops;
    }

    public void setExtraStops(List<String> extraStops) {
        this.extraStops = extraStops;
    }

    //=============

    public static LinePreviewDto convertToLinePreviewDto(Line line){
        LinePreviewDto dto = new LinePreviewDto();
        dto.setId(line.getId());
        dto.setName(line.getName());
        dto.setRouteEnd(line.getRouteEnd());
        dto.setRouteStart(line.getRouteStart());

        //set main stops
        List<Route> allRoutes = line.getRoutes();
        if (allRoutes == null || allRoutes.isEmpty()) return dto;

        List<BusStop> mainStops = line.getRoutes().getFirst().getStopsArr();

        dto.setMainStops(mainStops
                .stream().map(BusStop::getName).toList());

        if (allRoutes.size() == 1) return dto;

        //set unique extra stops if any (when Line has multiple Routes)
        List<BusStop> extraStops = new ArrayList<>();

        for (int i = 1; i < allRoutes.size(); i++) {
            allRoutes.get(i).getStopsArr().forEach(stop->{
                if (!mainStops.contains(stop) && !extraStops.contains(stop))
                    extraStops.add(stop);
            });
        }
        dto.setExtraStops(extraStops
                .stream().map(stop->{
                    return stop != null ? stop.getName() : "handled null in BusStop array ;(";
                }).toList());

        return dto;
    }
}
