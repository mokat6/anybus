package info.anyksciaibus.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String routeStart;  //which city, not which station
    String routeEnd;    //which city, not which station
    String operator;    //bus company
    String anykStationPlatform;
    String price;

    //custom notes, such as WiFi YES, Vilnius AS platform #24, etc
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="line_id")
    private List<CustomLineNote> customNotes;


    //when enabled, browse line page will show schedules filtered yearlyRule only - e.g. winter / summer
    boolean enabledSeasonalYearlyRuleFilter;

    @Enumerated(EnumType.ORDINAL)
    RouteType routeType;

    //fetch = FetchType.EAGER
//    @JsonIgnore
    @OneToMany(mappedBy = "line", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIdentityInfo(
//            generator = ObjectIdGenerators.PropertyGenerator.class,
//            property = "id")
    List<Route> routes;



    // =====================================================


    public Line() {
    }

    public Line(Long id) {
        this.id = id;
    }

    public Line(String name, String routeStart, String routeEnd, String operator, String anykStationPlatform, String price, RouteType routeType, List<Route> route) {
        this.name = name;
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
        this.operator = operator;
        this.anykStationPlatform = anykStationPlatform;
        this.price = price;
        this.routeType = routeType;
        this.routes = route;
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

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
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
}
