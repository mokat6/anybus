package info.anyksciaibus.entities.timeconstraints;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PublicHoliday {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private int month;
    private int day;





    //===========================================
    public PublicHoliday() {
    }

    public PublicHoliday(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
