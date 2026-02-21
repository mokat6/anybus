package info.anyksciaibus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;

import java.time.DayOfWeek;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //in human language
    String timeConstraintsDescription;



    //availability time constraints
    //Rules by week
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "runsOnWeekly") // Specify the name of the collection table
    @Enumerated(EnumType.STRING)
    List<DayOfWeek> runsOnWeekly;   //which days of the week

    //Rules by year
    @ManyToOne  // need cascade or no? was ALL, now deleted
    RunsOnYearly runsOnYearly;       //which periods like summer, schooldays,...

    boolean runsOnPublicHolidays; //true = runs on public holidays

    @OneToMany (mappedBy = "schedule", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Trip1Way> trips;

    @JsonIgnore
    @ManyToOne
    Line line;
    //============================================
    public Schedule() {
    }

    public Schedule(String timeConstraintsDescription,  List<Trip1Way> trips) {
        this.timeConstraintsDescription = timeConstraintsDescription;
//        this.runsOnWeekly = runsOnWeekly;
//        this.runsOnYearly = runsOnYearly;
//        this.runsOnPublicHolidays = runsOnPublicHolidays;
        this.trips = trips;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeConstraintsDescription() {
        return timeConstraintsDescription;
    }

    public void setTimeConstraintsDescription(String timeConstraintsDescription) {
        this.timeConstraintsDescription = timeConstraintsDescription;
    }

    public List<DayOfWeek> getRunsOnWeekly() {
        return runsOnWeekly;
    }

    public void setRunsOnWeekly(List<DayOfWeek> runsOnWeekly) {
        this.runsOnWeekly = runsOnWeekly;
    }

    public RunsOnYearly getRunsOnYearly() {
        return runsOnYearly;
    }

    public void setRunsOnYearly(RunsOnYearly runsOnYearly) {
        this.runsOnYearly = runsOnYearly;
    }

    public boolean isRunsOnPublicHolidays() {
        return runsOnPublicHolidays;
    }

    public void setRunsOnPublicHolidays(boolean runsOnPublicHolidays) {
        this.runsOnPublicHolidays = runsOnPublicHolidays;
    }

    public List<Trip1Way> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip1Way> trips) {
        this.trips = trips;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
