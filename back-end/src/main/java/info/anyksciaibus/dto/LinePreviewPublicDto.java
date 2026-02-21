package info.anyksciaibus.dto;

import info.anyksciaibus.entities.BusStop;
import info.anyksciaibus.entities.Line;
import info.anyksciaibus.entities.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LinePreviewPublicDto {
    Long id;
    String name;
    String goesTo;

    List<Map<String, String>> mainStops;
    List<Map<String, String>> extraStops;

    //=============================

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

    public String getGoesTo() {
        return goesTo;
    }

    public void setGoesTo(String goesTo) {
        this.goesTo = goesTo;
    }

    public List<Map<String, String>> getMainStops() {
        return mainStops;
    }

    public void setMainStops(List<Map<String, String>> mainStops) {
        this.mainStops = mainStops;
    }

    public List<Map<String, String>> getExtraStops() {
        return extraStops;
    }

    public void setExtraStops(List<Map<String, String>> extraStops) {
        this.extraStops = extraStops;
    }

    //=============

    public static LinePreviewPublicDto convertToLinePreviewDto(Line line){
        LinePreviewPublicDto dto = new LinePreviewPublicDto();
        dto.setId(line.getId());
        dto.setName(line.getName());
        dto.setGoesTo(line.getRouteEnd());


        //set main stops
        List<Route> allRoutes = line.getRoutes();
        if (allRoutes == null || allRoutes.isEmpty()) return dto;

        List<BusStop> mainStops = line.getRoutes().getFirst().getStopsArr();

        dto.setMainStops(mainStops.stream().map(stop ->
                Map.of("name",stop.getName(),"id",stop.getId().toString())).toList());

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
                    if (stop != null)
                        return Map.of("name",stop.getName(),"id",stop.getId().toString());
                    return null;
                }).toList());

        return dto;
    }
}
