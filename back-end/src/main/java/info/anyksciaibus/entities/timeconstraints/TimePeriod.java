package info.anyksciaibus.entities.timeconstraints;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TimePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int startMonth;
    int startDay;

    int endMonth;
    int endDay;


    //===================


    public TimePeriod() {
    }

    public TimePeriod(int startMonth, int startDay, int endMonth, int endDay) {
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endMonth = endMonth;
        this.endDay = endDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }
}


