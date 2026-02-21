package info.anyksciaibus.dto;

public class SingleStop {
    Long id;
    String timePoint;
    String stopName;
//    String distanceToNext;


    public SingleStop() {
    }

    public SingleStop(Long id, String timePoint, String stopName) {
        this.id = id;
        this.timePoint = timePoint;
        this.stopName = stopName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }
}
