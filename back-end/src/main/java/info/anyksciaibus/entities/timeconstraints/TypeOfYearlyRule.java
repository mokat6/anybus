package info.anyksciaibus.entities.timeconstraints;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfYearlyRule {
    //check if the date is within the Fixed Time Period
    FIXED_TIME_PERIOD("Static dates"),

    //check if the date passes the method - pattern #1
    DYNAMIC_PATTERN1_EACH_XDAY_OF_MONTH("Formula pattern"),

    //combo of the two
    COMBO_FIXED_AND_DYNAMIC_PATTERN1("Combo");



    private final String description;

    TypeOfYearlyRule(String description){
        this.description = description;
    }

    @JsonValue
    public String getDescription(){
        return description;
    }

    //    @JsonCreator     //for deserializing, not really needed now
    // Static method to retrieve enum constant by description
    public static TypeOfYearlyRule getByDescription(String description) {
        for (TypeOfYearlyRule type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        return null; // Or throw IllegalArgumentException("Invalid description: " + description);
    }


    }
