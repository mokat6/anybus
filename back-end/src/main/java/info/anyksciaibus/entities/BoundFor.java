package info.anyksciaibus.entities;


import com.fasterxml.jackson.annotation.JsonValue;

public enum BoundFor {
    TWO_WAYS_CITY_BOUND("City bound"),
    TWO_WAYS_OUT_BOUND("Out bound"),
    ONE_WAY("One way");


    private final String description;

    BoundFor(String description){
        this.description = description;
    }

    @JsonValue
    public String getDescription(){
        return description;
    }

    //    @JsonCreator     //for deserializing, not really needed now
    // Static method to retrieve enum constant by description
    public static BoundFor getByDescription(String description) {
        for (BoundFor type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        return null; // Or throw IllegalArgumentException("Invalid description: " + description);
    }
    }
