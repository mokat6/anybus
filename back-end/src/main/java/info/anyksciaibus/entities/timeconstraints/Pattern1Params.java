package info.anyksciaibus.entities.timeconstraints;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.DayOfWeek;

/**
 * This class holds a set of parameters for -> Yearly pattern #1.<br>
 * ENUM value - DYNAMIC_PATTERN1_EACH_XDAY_OF_MONTH<br>
 * Dynamic Pattern is contrasted with Fixed Days Period.<br>
 * Every Nth Xday Each Month (e.g. every second Thursday each month)
 * e.g. First Wednesday of the month  nthOccurenceEachMonth = 1    (not 0)
 */



@Entity
public class Pattern1Params {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    DayOfWeek desiredXday;
    int nthOccurenceEachMonth;




    //==============================================


    public Pattern1Params() {
    }

    public Pattern1Params(DayOfWeek dayOfWeek, int nthOccurenceEachMonth) {
        this.desiredXday = dayOfWeek;
        this.nthOccurenceEachMonth = nthOccurenceEachMonth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return desiredXday;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.desiredXday = dayOfWeek;
    }

    public int getNthOccurenceEachMonth() {
        return nthOccurenceEachMonth;
    }

    public void setNthOccurenceEachMonth(int nthOccurenceEachMonth) {
        this.nthOccurenceEachMonth = nthOccurenceEachMonth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pattern1Params{");
        sb.append("id=").append(id);
        sb.append(", desiredXday=").append(desiredXday);
        sb.append(", nthOccurenceEachMonth=").append(nthOccurenceEachMonth);
        sb.append('}');
        return sb.toString();
    }
}
