package info.anyksciaibus.dto;

import info.anyksciaibus.entities.BusStop;

public class BusStopsDto {
    //actually it's ID
    Long value;

    String label;







    //==================

    public static BusStopsDto busStopToDto(BusStop busStop) {
        if (busStop == null) return null;

        BusStopsDto dto = new BusStopsDto();
        dto.setLabel(busStop.getName());
        dto.setValue(busStop.getId());
        return dto;
    }

    public BusStopsDto() {
    }

    public BusStopsDto(Long value, String label) {

        this.value = value;
        this.label = label;
    }



    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
