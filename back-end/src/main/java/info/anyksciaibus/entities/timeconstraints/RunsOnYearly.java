package info.anyksciaibus.entities.timeconstraints;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class RunsOnYearly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String periodName;

    //FIXED_TIME_PERIOD                   -> check timePeriods if the date within the fixed dates
    //DYNAMIC_PATTERN1_EACH_XDAY_OF_MONTH -> check if pattern1 method passes with the given params
    TypeOfYearlyRule typeOfYearlyRule;

    //FIXED_TIME_PERIOD
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "runs_on_yearly_id")
    List<TimePeriod> timePeriods;

    //-----------------
    //if timePeriods is empty, will copy time periods from another rule
    @ManyToOne
    @JoinColumn(name = "copy_time_periods_from_other_rule_id", nullable = true)
    @JsonProperty("otherRuleId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    RunsOnYearly copyTimePeriodsFromOtherRule;

    @JsonSetter
    public void setOtherRuleId(Long id){
        if (id != null)
            this.copyTimePeriodsFromOtherRule = new RunsOnYearly(id);
    }


    //run this before working with this object
    public void updateFromDateRangeRefs() {
        if (timePeriods.isEmpty() && copyTimePeriodsFromOtherRule != null)
            setTimePeriods(copyTimePeriodsFromOtherRule.getTimePeriods());
       }
    //-----------------


    //DYNAMIC_PATTERN1_EACH_XDAY_OF_MONTH
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "runs_on_yearly_id")
    List<Pattern1Params> pattern1Params;



    //pattern 1 - Every Nth Xday Each Month (e.g. every second Thursday each month)
    //First
    public boolean testPattern1(LocalDate date) {

        if (this.pattern1Params.isEmpty()) return true;

        for (Pattern1Params paramPair : this.pattern1Params) {
            System.out.println("should be 4 times");
            System.out.println(paramPair);
            //Xday = Monday, Tuesday, ...
            if (date.getDayOfWeek() != paramPair.desiredXday) {
                continue;
            }

            //Find date, which is the first Xday in the month
            LocalDate firstXdayInMonth = date.withDayOfMonth(1);
            while (firstXdayInMonth.getDayOfWeek().ordinal() != paramPair.desiredXday.ordinal()) {
                firstXdayInMonth = firstXdayInMonth.plusDays(1);
            }

            int difference = date.getDayOfMonth() - firstXdayInMonth.getDayOfMonth();
            float occurence = difference / 7F;
            //occurence == 0  -> first Xday in the month
            //          == 1  -> second
            //          == 2  -> third
            //          == 3  -> fourth
            System.out.println("difference: "+difference);
            System.out.println("test result: --> " + (occurence + 1F) + "and there::::" +(float) paramPair.nthOccurenceEachMonth);
            if (occurence + 1F == (float) paramPair.nthOccurenceEachMonth ) {
                System.out.println("returns as TRUE!");
                return true;
            }
        }


        return false;
    }


    //=============================================


    public RunsOnYearly(Long id) {
        this.id = id;
    }

    public RunsOnYearly() {
    }

    public RunsOnYearly(String periodName, TypeOfYearlyRule typeOfYearlyRule, List<TimePeriod> timePeriods, List<Pattern1Params> pattern1Params) {
        this.periodName = periodName;
        this.typeOfYearlyRule = typeOfYearlyRule;
        this.timePeriods = timePeriods;
        this.pattern1Params = pattern1Params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public TypeOfYearlyRule getTypeOfYearlyRule() {
        return typeOfYearlyRule;
    }

    public void setTypeOfYearlyRule(TypeOfYearlyRule typeOfYearlyRule) {
        this.typeOfYearlyRule = typeOfYearlyRule;
    }

//    public List<TimePeriod> getTimePeriods() {
//        return timePeriods;
//    }

    public void setTimePeriods(List<TimePeriod> timePeriods) {
        this.timePeriods = timePeriods;
    }

    public List<Pattern1Params> getPattern1Params() {
        return pattern1Params;
    }

    public void setPattern1Params(List<Pattern1Params> pattern1Params) {
        this.pattern1Params = pattern1Params;
    }

    public RunsOnYearly getCopyTimePeriodsFromOtherRule() {
        return copyTimePeriodsFromOtherRule;
    }

    public void setCopyTimePeriodsFromOtherRule(RunsOnYearly copyTimePeriodsFromOtherRule) {
        this.copyTimePeriodsFromOtherRule = copyTimePeriodsFromOtherRule;
    }

    public List<TimePeriod> getTimePeriods() {
        return timePeriods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunsOnYearly that = (RunsOnYearly) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

