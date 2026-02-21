package info.anyksciaibus.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import info.anyksciaibus.dto.BusStopsDto;

@Entity
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String coords;
    boolean defaultOption;





    //================================================

    public BusStop() {
    }

    public BusStop(Long id) {
        this.id = id;
    }

    public BusStop(String name, String coords, boolean defaultOption){
        this.name = name;
        this.coords = coords;
        this.defaultOption = defaultOption;
    }

    public BusStop(String name, String coords) {
        this.name = name;
        this.coords = coords;
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

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public boolean isDefaultOption() {
        return defaultOption;
    }

    public void setDefaultOption(boolean defaultOption) {
        this.defaultOption = defaultOption;
    }

    public BusStopsDto mapToDto(){
        BusStopsDto dto = new BusStopsDto();
        String coordNote = this.getCoords();

        dto.setLabel(this.name + (coordNote != null && !coordNote.isEmpty() ? " (" + coordNote + ")" : "")     );
        dto.setValue(this.id);
        return dto;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusStop{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", coords='").append(coords).append('\'');
        sb.append(", defaultOption=").append(defaultOption);
        sb.append('}');
        return sb.toString();
    }
}
