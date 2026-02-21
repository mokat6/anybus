package info.anyksciaibus.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RouteType {
    CITY_BUS("City Bus"),
    REGIONAL_BUS("Regional Bus"),
    NATIONAL_BUS("Nationial Bus");

    private final String description;

    RouteType(String description){
        this.description = description;
    }

    @JsonValue
    public String getDescription(){
        return description;
    }

//    @JsonCreator     //for deserializing, not really needed now
    // Static method to retrieve enum constant by description
    public static RouteType getByDescription(String description) {
        for (RouteType type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        return null; // Or throw IllegalArgumentException("Invalid description: " + description);
    }
}
