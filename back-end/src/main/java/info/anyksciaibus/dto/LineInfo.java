package info.anyksciaibus.dto;

import info.anyksciaibus.entities.CustomLineNote;
import info.anyksciaibus.entities.Line;
import info.anyksciaibus.entities.RouteType;

import java.util.List;
import java.util.Objects;

public class LineInfo {
    Long id;
    String name;
    String routeStart;  //which city, not which station
    String routeEnd;    //which city, not which station
    String operator;    //bus company
    String anykStationPlatform;
    String price;
    boolean enabledSeasonalYearlyRuleFilter;
    List<CustomLineNote> customNotes;

    RouteType routeType;


    //===================================
    public LineInfo() {
    }

    public LineInfo(Long id, String name, String routeStart, String routeEnd,  String operator, String anykStationPlatform, String price, RouteType routeType) {
        this.id = id;
        this.name = name;
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
        this.operator = operator;
        this.anykStationPlatform = anykStationPlatform;
        this.price = price;
        this.routeType = routeType;
    }

    public static LineInfo LineToDto(Line line){
        LineInfo dto = new LineInfo();
        dto.setName(line.getName());
        dto.setId(line.getId());
        dto.setRouteStart(line.getRouteStart());
        dto.setRouteEnd(line.getRouteEnd());
        dto.setPrice(line.getPrice());
        dto.setAnykStationPlatform(line.getAnykStationPlatform());
        dto.setOperator(line.getOperator());
        dto.setRouteType(line.getRouteType());
        dto.setEnabledSeasonalYearlyRuleFilter(line.isEnabledSeasonalYearlyRuleFilter());
        dto.setCustomNotes(line.getCustomNotes());
        return dto;
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

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public boolean isEnabledSeasonalYearlyRuleFilter() {
        return enabledSeasonalYearlyRuleFilter;
    }
    public void setEnabledSeasonalYearlyRuleFilter(boolean enabledSeasonalYearlyRuleFilter) {
        this.enabledSeasonalYearlyRuleFilter = enabledSeasonalYearlyRuleFilter;
    }

    public List<CustomLineNote> getCustomNotes() {
        return customNotes;
    }

    public void setCustomNotes(List<CustomLineNote> customNotes) {
        this.customNotes = customNotes;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineInfo lineInfo = (LineInfo) o;
        return Objects.equals(id, lineInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
