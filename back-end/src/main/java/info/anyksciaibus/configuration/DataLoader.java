package info.anyksciaibus.configuration;

import info.anyksciaibus.entities.*;
import info.anyksciaibus.entities.timeconstraints.*;
import info.anyksciaibus.repositories.*;
import info.anyksciaibus.services.timeconstraints.PublicHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {


    BusStopRepo busStopRepo;
    LineRepo lineRepo;
    RouteRepo routeRepo;
    ScheduleRepo scheduleRepo;
    PublicHolidayRepo holidayRepo;
    RunsOnYearlyRepo royRepo;

    @Autowired
    PublicHolidayService phs;

    public DataLoader(PublicHolidayRepo holidayRepo, BusStopRepo busStopRepo, LineRepo lineRepo, RouteRepo routeRepo, ScheduleRepo scheduleRepo,  RunsOnYearlyRepo royRepo){
        this.busStopRepo = busStopRepo;
        this.lineRepo = lineRepo;
        this.routeRepo = routeRepo;
        this.scheduleRepo = scheduleRepo;
        this.holidayRepo = holidayRepo;
        this.royRepo = royRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        //loading dummy data

        //Line 1
//        createBusStops();
//        createLine();
//        createRoutes();
//        createSchedule();
//
//        createBusStops2();
//        createLine2();
//        createRoutes2();
//        createSchedule2();
//
//        createBusStops3();
//        createLine3();
//        createRoutes3();
//        createSchedule3();
//
//
//        setTimeConstraints();
//        setTimeConstraints2();
//        setPublicHolidays();



    }


    public void setPublicHolidays(){
        PublicHoliday h1 = new PublicHoliday("Naujieji metai", 1, 1);
        PublicHoliday h2 = new PublicHoliday("Lietuvos valstybės atkūrimo diena", 2, 16);
        PublicHoliday h3 = new PublicHoliday("Lietuvos nepriklausomybės atkūrimo diena", 3, 11);
        PublicHoliday h4 = new PublicHoliday("Tarptautinė darbo diena", 5, 1);
        PublicHoliday h5 = new PublicHoliday("Rasos ir Joninių diena", 6, 24);
        PublicHoliday h6 = new PublicHoliday("Lietuvos karaliaus Mindaugo karūnavimo diena", 7, 6);
        PublicHoliday h7 = new PublicHoliday("Žolinė", 8, 15);
        PublicHoliday h8 = new PublicHoliday("Visų Šventųjų diena", 11, 1);
        PublicHoliday h9 = new PublicHoliday("Mirusiųjų atminimo (Vėlinių) diena", 11, 2);
        PublicHoliday h10 = new PublicHoliday("Kūčios", 12, 24);
        PublicHoliday h11 = new PublicHoliday("Kalėdos 1d", 12, 25);
        PublicHoliday h12 = new PublicHoliday("Kalėdos 2d", 12, 26);

        holidayRepo.saveAll(List.of(h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12));


    }

    public void setTimeConstraints(){
        Schedule s2 = scheduleRepo.findById(2l).get();
        s2.setRunsOnWeekly(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));

        RunsOnYearly yearlyRules2 = new RunsOnYearly();
        yearlyRules2.setPeriodName("All year round");
        yearlyRules2.setTypeOfYearlyRule(TypeOfYearlyRule.FIXED_TIME_PERIOD);
        yearlyRules2.setTimePeriods(List.of(new TimePeriod(1,1,12,31)));
        s2.setRunsOnYearly(yearlyRules2);

        s2.setRunsOnPublicHolidays(false);

        //---------

        Schedule s1 = scheduleRepo.findById(1l).get();
        s1.setRunsOnWeekly(List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.MONDAY));

        RunsOnYearly yearlyRules1 = new RunsOnYearly();
        yearlyRules1.setPeriodName("Student summer holiday");
        yearlyRules1.setTypeOfYearlyRule(TypeOfYearlyRule.FIXED_TIME_PERIOD);
        yearlyRules1.setTimePeriods(List.of(new TimePeriod(6,15,8,31)));
        s1.setRunsOnYearly(yearlyRules1);

        s1.setRunsOnPublicHolidays(false);

        //---------

        Schedule s3 = scheduleRepo.findById(3l).get();
        s3.setRunsOnWeekly(List.of(DayOfWeek.THURSDAY));

        RunsOnYearly yearlyRules3 = new RunsOnYearly();
        yearlyRules3.setPeriodName("2nd and 4th Thursday of the month");
        yearlyRules3.setTypeOfYearlyRule(TypeOfYearlyRule.DYNAMIC_PATTERN1_EACH_XDAY_OF_MONTH);
        yearlyRules3.setPattern1Params(List.of(
                new Pattern1Params(DayOfWeek.THURSDAY, 2),
                new Pattern1Params(DayOfWeek.THURSDAY, 4)
        ));
        s3.setRunsOnYearly(yearlyRules3);

        s3.setRunsOnPublicHolidays(false);

        //-------

        Schedule s4 = scheduleRepo.findById(4l).get();
        s4.setRunsOnWeekly(List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));

        RunsOnYearly yearlyRules4 = new RunsOnYearly();
        yearlyRules4.setPeriodName("summer season 04-01 to 11-15");
        yearlyRules4.setTypeOfYearlyRule(TypeOfYearlyRule.FIXED_TIME_PERIOD);
        yearlyRules4.setTimePeriods(List.of(new TimePeriod(4,1,11,15)));
        s4.setRunsOnYearly(yearlyRules4);

        s4.setRunsOnPublicHolidays(true);

        //--------

        Schedule s5 = scheduleRepo.findById(5l).get();
        s5.setRunsOnWeekly(List.of(DayOfWeek.WEDNESDAY));

        RunsOnYearly yearlyRules5 = new RunsOnYearly();
        yearlyRules5.setPeriodName("1st and 3rd Wednesday of the month");
        yearlyRules5.setTypeOfYearlyRule(TypeOfYearlyRule.DYNAMIC_PATTERN1_EACH_XDAY_OF_MONTH);
        yearlyRules5.setPattern1Params(List.of(
                new Pattern1Params(DayOfWeek.WEDNESDAY, 1),
                new Pattern1Params(DayOfWeek.WEDNESDAY, 3)
        ));
        s5.setRunsOnYearly(yearlyRules5);

        s5.setRunsOnPublicHolidays(true);

        //----------

        Schedule s6 = scheduleRepo.findById(6l).get();
        s6.setRunsOnWeekly(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));

        RunsOnYearly yearlyRules6 = new RunsOnYearly();
        yearlyRules6.setPeriodName("Schooldays");
        yearlyRules6.setTypeOfYearlyRule(TypeOfYearlyRule.FIXED_TIME_PERIOD);
        yearlyRules6.setTimePeriods(List.of(
                new TimePeriod(1,15,3,29),
                new TimePeriod(4,12,6,17),
                new TimePeriod(9,1,11,1),
                new TimePeriod(11,7,12,23)
        ));
        s6.setRunsOnYearly(yearlyRules6);

        s6.setRunsOnPublicHolidays(false);

        //-----==============================================


        scheduleRepo.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));
    }

    public void setTimeConstraints2(){

        Schedule s7 = scheduleRepo.findById(7l).get();
        s7.setRunsOnWeekly(List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY));
        RunsOnYearly roy7 = royRepo.findById(6L).get();
        s7.setRunsOnYearly(roy7);
        s7.setRunsOnPublicHolidays(false);

        //----

        Schedule s8 = scheduleRepo.findById(8l).get();
        s8.setRunsOnWeekly(List.of( DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY));
        RunsOnYearly roy8 = royRepo.findById(2L).get();
        s8.setRunsOnYearly(roy8);
        s8.setRunsOnPublicHolidays(true);

//        Schedule s9 = scheduleRepo.findById(9l).get();
//        s9.setRunsOnWeekly(List.of( DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY, DayOfWeek.SATURDAY));
//        RunsOnYearly roy9 = royRepo.findById(1L).get();
//        s9.setRunsOnYearly(roy9);
//        s9.setRunsOnPublicHolidays(true);
//
//        Schedule s10 = scheduleRepo.findById(10l).get();
//        s10.setRunsOnWeekly(List.of( DayOfWeek.TUESDAY, DayOfWeek.SUNDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));
//        RunsOnYearly roy10 = royRepo.findById(3L).get();
//        s10.setRunsOnYearly(roy10);
//        s10.setRunsOnPublicHolidays(true);
//
//        Schedule s11 = scheduleRepo.findById(11l).get();
//        s11.setRunsOnWeekly(List.of( DayOfWeek.THURSDAY, DayOfWeek.SUNDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.MONDAY));
//        RunsOnYearly roy11 = royRepo.findById(4L).get();
//        s11.setRunsOnYearly(roy11);
//        s11.setRunsOnPublicHolidays(false);
//
//        Schedule s12 = scheduleRepo.findById(12l).get();
//        s12.setRunsOnWeekly(List.of( DayOfWeek.TUESDAY, DayOfWeek.SUNDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.MONDAY));
//        RunsOnYearly roy12 = royRepo.findById(3L).get();
//        s12.setRunsOnYearly(roy12);
//        s12.setRunsOnPublicHolidays(false);

        scheduleRepo.saveAll(Arrays.asList(s7, s8));

    }

    //<editor-fold desc="LINE 1">
    public void createBusStops(){
        BusStop bs1 = new BusStop("Anykščių AS", "", true);
        BusStop bs2 = new BusStop("A. Baranausko a.", "");
        BusStop bs3 = new BusStop("Poliklinika", "");
        BusStop bs4 = new BusStop("Anykščių MSV", "");
        BusStop bs5 = new BusStop("Šlavėnai", "");
        BusStop bs6 = new BusStop("Puntukas", "", true);
        BusStop bs7 = new BusStop("Gražumynas", "");
        BusStop bs8 = new BusStop("Šližiai", "");
        BusStop bs9 = new BusStop("Pavirinčiai", "");
        BusStop bs10 = new BusStop("Kurkliai", "", true);
        BusStop bs11 = new BusStop("Moliakalnis", "");
        BusStop bs12 = new BusStop("Staškūniškis", "");

        List<BusStop> bsList = List.of(bs1, bs2, bs3, bs4, bs5, bs6, bs7, bs8, bs9, bs10, bs11, bs12);
        busStopRepo.saveAll(bsList);
    }

    private void createLine() {
        Line line1 = new Line("M17-1", "Anykščiai", "Staškūniškis",  "Transporto Centras", "6", "Ask driver", RouteType.REGIONAL_BUS, List.of(new Route()) );
        lineRepo.save(line1);
    }

    public void createRoutes(){

        List<BusStop> stopsArr = new ArrayList<>();
        stopsArr.add(new BusStop(1L));
        stopsArr.add(new BusStop(2L));
        stopsArr.add(new BusStop(3L));
        stopsArr.add(new BusStop(4L));
        stopsArr.add(new BusStop(5L));
        stopsArr.add(new BusStop(6L));
        stopsArr.add(new BusStop(7L));
        stopsArr.add(new BusStop(8L));
        stopsArr.add(new BusStop(9L));
        stopsArr.add(new BusStop(10L));
        stopsArr.add(new BusStop(11L));
        stopsArr.add(new BusStop(12L));

        Line line = new Line();
        line.setId(1L);

        List<Integer> distArr = new ArrayList<>();
        distArr.add(1000);
        distArr.add(500);
        distArr.add(2400);
        distArr.add(1400);
        distArr.add(2000);
        distArr.add(2500);
        distArr.add(1600);
        distArr.add(3000);
        distArr.add(2300);
        distArr.add(2400);


        Route route1 = new Route(stopsArr, distArr, line, "");

        routeRepo.save(route1);
    }

    public void createSchedule(){
        schedule1();
        schedule3();
    }

    public void schedule1(){
        Route route1 = new Route();
        route1.setId(1L);
        List<LocalTime> timeArr = new ArrayList<>();

        timeArr.add(LocalTime.of(8,55));
        timeArr.add(LocalTime.of(8,56));
        timeArr.add(LocalTime.of(8,58));
        timeArr.add(LocalTime.of(9,3));
        timeArr.add(LocalTime.of(9,5));
        timeArr.add(LocalTime.of(9,7));
        timeArr.add(LocalTime.of(9,10));
        timeArr.add(LocalTime.of(9,13));
        timeArr.add(LocalTime.of(9,15));
        timeArr.add(LocalTime.of(9,18));
        timeArr.add(LocalTime.of(9,20));
        timeArr.add(LocalTime.of(9,25));
//(Boolean isRouteDirReversed, BoundFor boundFor, List<LocalTime> timeList, Route route) {

Trip1Way trip1 = new Trip1Way(false, BoundFor.TWO_WAYS_OUT_BOUND, timeArr, route1);

        List<LocalTime> timeArr2 = new ArrayList<>();

        timeArr2.add(LocalTime.of(9,25));
        timeArr2.add(LocalTime.of(9,28));
        timeArr2.add(LocalTime.of(9,30));
        timeArr2.add(LocalTime.of(9,32));
        timeArr2.add(LocalTime.of(9,34));
        timeArr2.add(LocalTime.of(9,36));
        timeArr2.add(LocalTime.of(9,38));
        timeArr2.add(LocalTime.of(9,40));
        timeArr2.add(LocalTime.of(9,42));
        timeArr2.add(LocalTime.of(9,46));
        timeArr2.add(LocalTime.of(9,48));
        timeArr2.add(LocalTime.of(9,50));


Trip1Way trip2 = new Trip1Way(true, BoundFor.TWO_WAYS_CITY_BOUND, timeArr2, route1);
        Schedule schedule = new Schedule("važiuoja moksleivių atostogų metu", List.of(trip1,trip2));
        Line line = new Line();
        line.setId(1L);
        schedule.setLine(line);

        scheduleRepo.save(schedule);
    }


    public void schedule3(){
        Route route1 = new Route();
        route1.setId(1L);
        List<LocalTime> timeArr1 = new ArrayList<>();

        timeArr1.add(LocalTime.of(13,30));
        timeArr1.add(LocalTime.of(13,31));
        timeArr1.add(LocalTime.of(13,33));
        timeArr1.add(LocalTime.of(13,35));
        timeArr1.add(LocalTime.of(13,37));
        timeArr1.add(LocalTime.of(13,39));
        timeArr1.add(LocalTime.of(13,41));
        timeArr1.add(LocalTime.of(13,43));
        timeArr1.add(LocalTime.of(13,45));
        timeArr1.add(LocalTime.of(13,47));
        timeArr1.add(LocalTime.of(13,49));
        timeArr1.add(LocalTime.of(13,54));

   Trip1Way trip1 = new Trip1Way(false, BoundFor.TWO_WAYS_OUT_BOUND, timeArr1, route1);


        List<LocalTime> timeArr2 = new ArrayList<>();

        timeArr2.add(LocalTime.of(13,55));
        timeArr2.add(LocalTime.of(13,58));
        timeArr2.add(LocalTime.of(14,0));
        timeArr2.add(LocalTime.of(14,3));
        timeArr2.add(LocalTime.of(14,6));
        timeArr2.add(LocalTime.of(14,8));
        timeArr2.add(LocalTime.of(14,10));
        timeArr2.add(LocalTime.of(14,13));
        timeArr2.add(LocalTime.of(14,15));
        timeArr2.add(LocalTime.of(14,17));
        timeArr2.add(LocalTime.of(14,19));
        timeArr2.add(LocalTime.of(14,20));

        Trip1Way trip2 = new Trip1Way(true, BoundFor.TWO_WAYS_CITY_BOUND, timeArr2, route1);

        Schedule schedule = new Schedule("važiuoja moksleivių atostogų metu", List.of(trip1,trip2));
        Line line = new Line();
        line.setId(1L);
        schedule.setLine(line);
        scheduleRepo.save(schedule);
    }
    //</editor-fold>
//=====================================================

    //<editor-fold desc="LINE 2">
    public void createBusStops2(){
        BusStop bs1 = new BusStop("Technologijos m.", "");
        BusStop bs2 = new BusStop("Šeimyniškiai", "");
        BusStop bs3 = new BusStop("Ažuožeriai", "");
        BusStop bs4 = new BusStop("Vaivadiškiai", "");
        BusStop bs5 = new BusStop("Zavesiškis", "");
        BusStop bs6 = new BusStop("Bebrūnai", "");
        BusStop bs7 = new BusStop("Kavarskas", "", true);
        BusStop bs8 = new BusStop("Janušava", "");
        BusStop bs9 = new BusStop("Gintaras", "");
        BusStop bs10 = new BusStop("Girninkija", "");
        BusStop bs11 = new BusStop("Pienionys", "");
        BusStop bs12 = new BusStop("Malgažatava", "");
        BusStop bs13 = new BusStop("Mokinių st.v.", "");
        BusStop bs14 = new BusStop("Repšėnai", "");
        BusStop bs15 = new BusStop("Degionys", "");
        BusStop bs16 = new BusStop("Pusbačkiai", "");
        BusStop bs17 = new BusStop("Surdaugiai", "");
        BusStop bs18 = new BusStop("Traupis", "");
        BusStop bs19 = new BusStop("Janapolis", "");
        BusStop bs20 = new BusStop("Levaniškis", "");
        BusStop bs21 = new BusStop("Vežiškiai", "", true);
        BusStop bs22 = new BusStop("Raguvos kr.", "");
        BusStop bs23 = new BusStop("Raguva", "");
        BusStop bs24 = new BusStop("Girelė", "");
        BusStop bs25 = new BusStop("Degsniai", "");
        BusStop bs26 = new BusStop("Žalioji", "");
        BusStop bs27 = new BusStop("Troškūnai", "");
        BusStop bs28 = new BusStop("Aukštakalnis", "");
        BusStop bs29 = new BusStop("Smėlynė I", "");
        BusStop bs30 = new BusStop("Smėlynė II", "");
        BusStop bs31 = new BusStop("Kryžkelė", "", true);
        BusStop bs32 = new BusStop("Naujonys", "");
        BusStop bs33 = new BusStop("Piktagalys", "");
        BusStop bs34 = new BusStop("Kuniškiai", "");
        BusStop bs35 = new BusStop("Vikonys", "");
        BusStop bs36 = new BusStop("Vėjališkis", "");

        List<BusStop> bsList = List.of(bs1, bs2, bs3, bs4, bs5, bs6, bs7, bs8, bs9, bs10, bs11, bs12, bs13, bs14, bs15, bs16, bs17, bs18, bs19, bs20, bs21, bs22, bs23, bs24, bs25, bs26, bs27, bs28, bs29, bs30, bs31, bs32, bs33, bs34, bs35, bs36);

        busStopRepo.saveAll(bsList);
    }

    private void createLine2() {
        Line line1 = new Line("M18-1-1", "Anykščiai AS", "Anykščiai AS",  "Transporto Centras", "1", "Ask driver", RouteType.REGIONAL_BUS, List.of(new Route()) );
        lineRepo.save(line1);
    }

    private void createRoutes2() {
        List<BusStop> stopsArr = new ArrayList<>();
        stopsArr.add(new BusStop(1L));
        stopsArr.add(new BusStop(13L));
        stopsArr.add(new BusStop(14L));
        stopsArr.add(new BusStop(15L));
        stopsArr.add(new BusStop(16L));
        stopsArr.add(new BusStop(17L));
        stopsArr.add(new BusStop(18L));
        stopsArr.add(new BusStop(19L));
        stopsArr.add(new BusStop(20L));
        stopsArr.add(new BusStop(21L));
        stopsArr.add(new BusStop(22L));
        stopsArr.add(new BusStop(23L));
        stopsArr.add(new BusStop(24L));
        stopsArr.add(new BusStop(25L));
        stopsArr.add(new BusStop(26L));
        stopsArr.add(new BusStop(27L));
        stopsArr.add(new BusStop(28L));
        stopsArr.add(new BusStop(29L));
        stopsArr.add(new BusStop(30L));
        stopsArr.add(new BusStop(31L));
        stopsArr.add(new BusStop(32L));
        stopsArr.add(new BusStop(33L));
        stopsArr.add(new BusStop(34L));
        stopsArr.add(new BusStop(35L));
        stopsArr.add(new BusStop(34L));
        stopsArr.add(new BusStop(33L));
        stopsArr.add(new BusStop(32L));
        stopsArr.add(new BusStop(31L));
        stopsArr.add(new BusStop(30L));
        stopsArr.add(new BusStop(36L));
        stopsArr.add(new BusStop(37L));
        stopsArr.add(new BusStop(38L));
        stopsArr.add(new BusStop(39L));
        stopsArr.add(new BusStop(40L));
        stopsArr.add(new BusStop(41L));
        stopsArr.add(new BusStop(42L));
        stopsArr.add(new BusStop(43L));
        stopsArr.add(new BusStop(44L));
        stopsArr.add(new BusStop(45L));
        stopsArr.add(new BusStop(46L));
        stopsArr.add(new BusStop(47L));
        stopsArr.add(new BusStop(48L));
        stopsArr.add(new BusStop(1L));

        Line line = new Line();
        line.setId(2L);

        List<Integer> distArr = new ArrayList<>();
        distArr.add(1500);
        distArr.add(2400);
        distArr.add(2100);
        distArr.add(3000);
        distArr.add(2000);
        distArr.add(3000);
        distArr.add(2000);
        distArr.add(2300);
        distArr.add(500);
        distArr.add(1100);
        distArr.add(2200);
        distArr.add(3000);
        distArr.add(1200);
        distArr.add(800);
        distArr.add(1000);
        distArr.add(2400);
        distArr.add(900);
        distArr.add(2200);
        distArr.add(2700);
        distArr.add(1500);
        distArr.add(2700);
        distArr.add(2500);
        distArr.add(1000);
        distArr.add(1000);
        distArr.add(2500);
        distArr.add(2700);
        distArr.add(1600);
        distArr.add(2600);
        distArr.add(3500);
        distArr.add(1900);
        distArr.add(2900);
        distArr.add(4600);
        distArr.add(1500);
        distArr.add(1500);
        distArr.add(2600);
        distArr.add(2300);
        distArr.add(1300);
        distArr.add(2700);
        distArr.add(1400);
        distArr.add(1200);
        distArr.add(800);
        distArr.add(3000);


        Route route1 = new Route(stopsArr, distArr, line, "");

        routeRepo.save(route1);
    }

    private void createSchedule2() {
        Route route1 = new Route();
        route1.setId(2L);
        List<LocalTime> timeArr = new ArrayList<>();

        timeArr.add(LocalTime.of(7,20));
        timeArr.add(LocalTime.of(7,22));
        timeArr.add(LocalTime.of(7,24));
        timeArr.add(LocalTime.of(7,25));
        timeArr.add(LocalTime.of(7,29));
        timeArr.add(LocalTime.of(7,30));
        timeArr.add(LocalTime.of(7,33));
        timeArr.add(LocalTime.of(7,35));
        timeArr.add(LocalTime.of(7,38));
        timeArr.add(LocalTime.of(7,40));
        timeArr.add(LocalTime.of(7,42));
        timeArr.add(LocalTime.of(7,45));
        timeArr.add(LocalTime.of(7,48));
        timeArr.add(LocalTime.of(7,49));
        timeArr.add(LocalTime.of(7,50));
        timeArr.add(LocalTime.of(7,51));
        timeArr.add(LocalTime.of(7,53));
        timeArr.add(LocalTime.of(7,54));
        timeArr.add(LocalTime.of(7,55));
        timeArr.add(LocalTime.of(7,57));
        timeArr.add(LocalTime.of(7,59));
        timeArr.add(LocalTime.of(8,2));
        timeArr.add(LocalTime.of(8,4));
        timeArr.add(LocalTime.of(8,5));
        timeArr.add(LocalTime.of(8,6));
        timeArr.add(LocalTime.of(8,8));
        timeArr.add(LocalTime.of(8,12));
        timeArr.add(LocalTime.of(8,15));
        timeArr.add(LocalTime.of(8,20));
        timeArr.add(LocalTime.of(8,22));
        timeArr.add(LocalTime.of(8,26));
        timeArr.add(LocalTime.of(8,34));
        timeArr.add(LocalTime.of(8,42));
        timeArr.add(LocalTime.of(8,45));
        timeArr.add(LocalTime.of(8,48));
        timeArr.add(LocalTime.of(8,53));
        timeArr.add(LocalTime.of(8,55));
        timeArr.add(LocalTime.of(9,0));
        timeArr.add(LocalTime.of(9,6));
        timeArr.add(LocalTime.of(9,9));
        timeArr.add(LocalTime.of(9,12));
        timeArr.add(LocalTime.of(9,13));
        timeArr.add(LocalTime.of(9,20));

        Trip1Way trip1 = new Trip1Way(false, BoundFor.ONE_WAY, timeArr, route1);

        Schedule schedule = new Schedule("Autobusas važiuoja trečiadieniais", List.of(trip1));
        Line line = new Line();
        line.setId(2L);
        schedule.setLine(line);

        scheduleRepo.save(schedule);

        //============================================= second schedule, afternoon

        Route route2 = new Route();
        route2.setId(2L);
        List<LocalTime> timeArr2 = new ArrayList<>();

        timeArr2.add(LocalTime.of(13,30));
        timeArr2.add(LocalTime.of(13,32));
        timeArr2.add(LocalTime.of(13,35));
        timeArr2.add(LocalTime.of(13,38));
        timeArr2.add(LocalTime.of(13,41));
        timeArr2.add(LocalTime.of(13,49));
        timeArr2.add(LocalTime.of(13,52));
        timeArr2.add(LocalTime.of(13,56));
        timeArr2.add(LocalTime.of(13,59));
        timeArr2.add(LocalTime.of(14,2));
        timeArr2.add(LocalTime.of(14,5));
        timeArr2.add(LocalTime.of(14,10));
        timeArr2.add(LocalTime.of(14,15));
        timeArr2.add(LocalTime.of(14,20));
        timeArr2.add(LocalTime.of(14,30));
        timeArr2.add(LocalTime.of(14,32));
        timeArr2.add(LocalTime.of(14,35));
        timeArr2.add(LocalTime.of(14,38));
        timeArr2.add(LocalTime.of(14,40));
        timeArr2.add(LocalTime.of(14,45));
        timeArr2.add(LocalTime.of(14,47));
        timeArr2.add(LocalTime.of(14,49));
        timeArr2.add(LocalTime.of(14,51));
        timeArr2.add(LocalTime.of(14,55));
        timeArr2.add(LocalTime.of(15,0));
        timeArr2.add(LocalTime.of(15,1));
        timeArr2.add(LocalTime.of(15,2));
        timeArr2.add(LocalTime.of(15,3));
        timeArr2.add(LocalTime.of(15,5));
        timeArr2.add(LocalTime.of(15,7));
        timeArr2.add(LocalTime.of(15,8));
        timeArr2.add(LocalTime.of(15,10));
        timeArr2.add(LocalTime.of(15,12));
        timeArr2.add(LocalTime.of(15,13));
        timeArr2.add(LocalTime.of(15,14));
        timeArr2.add(LocalTime.of(15,15));
        timeArr2.add(LocalTime.of(15,18));
        timeArr2.add(LocalTime.of(15,22));
        timeArr2.add(LocalTime.of(15,25));
        timeArr2.add(LocalTime.of(15,27));
        timeArr2.add(LocalTime.of(15,30));
        timeArr2.add(LocalTime.of(15,32));
        timeArr2.add(LocalTime.of(15,35));

        Trip1Way trip2 = new Trip1Way(false, BoundFor.ONE_WAY, timeArr2, route2);

        Schedule schedule2 = new Schedule("Autobusas važiuoja trečiadieniais", List.of(trip2));


        schedule2.setLine(line);
        scheduleRepo.save(schedule2);
    }
    //</editor-fold>
//=======================================================

    //<editor-fold desc="LINE 3">
    public void createBusStops3(){
        BusStop  bs1 = new BusStop("A.Vienuolio prog.", "");
        BusStop  bs2 = new BusStop("Stadijonas", "");
        BusStop  bs3 = new BusStop("SB ,,Voruta“", "");
        BusStop  bs4 = new BusStop("Elmininkai", "");
        BusStop  bs5 = new BusStop("Bikūnai", "");
        BusStop  bs6 = new BusStop("S.Elmininkai", "");
        BusStop  bs7 = new BusStop("Vosgėliai", "");
        BusStop  bs8 = new BusStop("Čekonys I", "");
        BusStop  bs9 = new BusStop("Čekonys II", "");
        BusStop bs10 = new BusStop("Kalveliai", "");
        BusStop bs11 = new BusStop("Meldučiai", "");
        BusStop bs12 = new BusStop("Debeikiai", "");
        BusStop bs13 = new BusStop("Jurzdikas", "");
        BusStop bs14 = new BusStop("Aknystų kr.", "");
        BusStop bs15 = new BusStop("Aknystos", "");

        BusStop bs16 = new BusStop("Šližiai", "");
        BusStop bs17 = new BusStop("Varkujai", "");



        List<BusStop> bsList = List.of(bs1, bs2, bs3, bs4, bs5, bs6, bs7, bs8, bs9, bs10, bs11, bs12, bs13, bs14, bs15, bs16, bs17);

        busStopRepo.saveAll(bsList);
    }

    private void createLine3() {
        Line line1 = new Line("M22", "ANYKŠČIAI", "VARKUJAI ",  "Transporto Centras", "3", "Ask driver", RouteType.REGIONAL_BUS, List.of(new Route()) );
        lineRepo.save(line1);
    }

    private void createRoutes3() {
        newRoute3_1();
        newRoute3_2();
        newRoute3_3();
    }

    public void newRoute3_1(){
        List<BusStop> stopsArr = new ArrayList<>();
        stopsArr.add(new BusStop(65L));
        stopsArr.add(new BusStop(64L));
        stopsArr.add(new BusStop(62L));
        stopsArr.add(new BusStop(63L));
        stopsArr.add(new BusStop(62L));
        stopsArr.add(new BusStop(61L));
        stopsArr.add(new BusStop(60L));
        stopsArr.add(new BusStop(59L));
        stopsArr.add(new BusStop(58L));
        stopsArr.add(new BusStop(57L));
        stopsArr.add(new BusStop(56L));
        stopsArr.add(new BusStop(55L));
        stopsArr.add(new BusStop(54L));
        stopsArr.add(new BusStop(53L));
        stopsArr.add(new BusStop(52L));
        stopsArr.add(new BusStop(51L));
        stopsArr.add(new BusStop(50L));
        stopsArr.add(new BusStop(49L));
        stopsArr.add(new BusStop(2L));
        stopsArr.add(new BusStop(1L));


        Line line = new Line();
        line.setId(3L);

        List<Integer> distArr = new ArrayList<>();
        distArr.add(1500);
        distArr.add(1500);
        distArr.add(900);
        distArr.add(900);
        distArr.add(1100);
        distArr.add(1900);
        distArr.add(1500);
        distArr.add(1600);
        distArr.add(2500);
        distArr.add(1100);
        distArr.add(2600);
        distArr.add(1700);
        distArr.add(3000);
        distArr.add(1300);
        distArr.add(1000);
        distArr.add(1900);
        distArr.add(1000);
        distArr.add(500);
        distArr.add(500);


        Route route1 = new Route(stopsArr, distArr, line, "");

        routeRepo.save(route1);
    }
    public void newRoute3_2(){
        List<BusStop> stopsArr = new ArrayList<>();
        stopsArr.add(new BusStop(1L));
        stopsArr.add(new BusStop(2L));
        stopsArr.add(new BusStop(49L));
        stopsArr.add(new BusStop(50L));
        stopsArr.add(new BusStop(51L));
        stopsArr.add(new BusStop(52L));
        stopsArr.add(new BusStop(53L));
        //no S.Elmininkai
        stopsArr.add(new BusStop(55L));
        stopsArr.add(new BusStop(56L));
        stopsArr.add(new BusStop(57L));
        stopsArr.add(new BusStop(58L));
        stopsArr.add(new BusStop(59L));
        stopsArr.add(new BusStop(60L));
        stopsArr.add(new BusStop(61L));
        stopsArr.add(new BusStop(62L));
        stopsArr.add(new BusStop(63L));
        stopsArr.add(new BusStop(62L));
        stopsArr.add(new BusStop(64L));
        stopsArr.add(new BusStop(65L));


        Line line = new Line();
        line.setId(3L);

        List<Integer> distArr = new ArrayList<>();
        distArr.add(500);
        distArr.add(500);
        distArr.add(1000);
        distArr.add(1900);
        distArr.add(1000);
        distArr.add(1300);
        //no S.Elminkai
        distArr.add(1700);
        distArr.add(2600);
        distArr.add(1100);
        distArr.add(2500);
        distArr.add(1600);
        distArr.add(1500);
        distArr.add(1900);
        distArr.add(1100);
        distArr.add(900);
        distArr.add(900);
        distArr.add(1500);
        distArr.add(1500);



        Route route1 = new Route(stopsArr, distArr, line, "be S.Elmininku");

        routeRepo.save(route1);
    }
    public void newRoute3_3(){
        List<BusStop> stopsArr = new ArrayList<>();
        stopsArr.add(new BusStop(1L));
        stopsArr.add(new BusStop(2L));
        stopsArr.add(new BusStop(49L));
        stopsArr.add(new BusStop(50L));
        stopsArr.add(new BusStop(51L));
        stopsArr.add(new BusStop(52L));
        stopsArr.add(new BusStop(53L));
        //no S.Elmininkai
        stopsArr.add(new BusStop(55L));
        stopsArr.add(new BusStop(56L));
        stopsArr.add(new BusStop(57L));
        stopsArr.add(new BusStop(58L));
        stopsArr.add(new BusStop(59L));
        stopsArr.add(new BusStop(60L));
        stopsArr.add(new BusStop(61L));
        stopsArr.add(new BusStop(62L));
        //no Aknystos
        //no Aknystu kr.
        stopsArr.add(new BusStop(64L));
        stopsArr.add(new BusStop(65L));


        Line line = new Line();
        line.setId(3L);

        List<Integer> distArr = new ArrayList<>();
        distArr.add(500);
        distArr.add(500);
        distArr.add(1000);
        distArr.add(1900);
        distArr.add(1000);
        distArr.add(1300);
        //no S.Elmininkai
        distArr.add(1700);
        distArr.add(2600);
        distArr.add(1100);
        distArr.add(2500);
        distArr.add(1600);
        distArr.add(1500);
        distArr.add(1900);
        distArr.add(1100);
        //no Aknystos
        //no Aknystu kr.
        distArr.add(1500);
        distArr.add(1500);



        Route route1 = new Route(stopsArr, distArr, line, "be S. Elmininku, Aknystos, Aknystu kr.");

        routeRepo.save(route1);
    }

    private void createSchedule3() {
        newSchedule3_1();
        newSchedule3_3();
        newSchedule3_4();
        newSchedule3_6();

    }
    private void newSchedule3_1(){
        Route route1 = new Route();
        route1.setId(5L);
        List<LocalTime> timeArr = new ArrayList<>();

        timeArr.add(LocalTime.of(6,30));
        timeArr.add(LocalTime.of(6,31));
        timeArr.add(LocalTime.of(6,32));
        timeArr.add(LocalTime.of(6,34));
        timeArr.add(LocalTime.of(6,35));
        timeArr.add(LocalTime.of(6,36));
        timeArr.add(LocalTime.of(6,39));
        timeArr.add(LocalTime.of(6,40));
        timeArr.add(LocalTime.of(6,41));
        timeArr.add(LocalTime.of(6,42));
        timeArr.add(LocalTime.of(6,44));
        timeArr.add(LocalTime.of(6,45));
        timeArr.add(LocalTime.of(6,48));
        timeArr.add(LocalTime.of(6,50));
        timeArr.add(LocalTime.of(6,51));
        timeArr.add(LocalTime.of(6,53));
        timeArr.add(LocalTime.of(6,55));

        Trip1Way trip1 = new Trip1Way(false, BoundFor.TWO_WAYS_OUT_BOUND, timeArr, route1);


        List<LocalTime> timeArr2 = new ArrayList<>();

        timeArr2.add(LocalTime.of(6,55));
        timeArr2.add(LocalTime.of(6,58));
        timeArr2.add(LocalTime.of(7,2));
        timeArr2.add(LocalTime.of(7,8));
        timeArr2.add(LocalTime.of(7,10));
        timeArr2.add(LocalTime.of(7,12));
        timeArr2.add(LocalTime.of(7,18));
        timeArr2.add(LocalTime.of(7,20));
        timeArr2.add(LocalTime.of(7,22));
        timeArr2.add(LocalTime.of(7,24));
        timeArr2.add(LocalTime.of(7,26));
        timeArr2.add(LocalTime.of(7,30));
        timeArr2.add(LocalTime.of(7,35));
        timeArr2.add(LocalTime.of(7,38));
        timeArr2.add(LocalTime.of(7,40));
        timeArr2.add(LocalTime.of(7,42));
        timeArr2.add(LocalTime.of(7,44));
        timeArr2.add(LocalTime.of(7,45));
        timeArr2.add(LocalTime.of(7,48));
        timeArr2.add(LocalTime.of(7,50));

        Trip1Way trip2 = new Trip1Way(false, BoundFor.TWO_WAYS_CITY_BOUND, timeArr2, route1);


        Schedule schedule = new Schedule("važiuoja darbo dienomis", List.of(trip1, trip2));
        Line line = new Line();
        line.setId(3L);
        schedule.setLine(line);
        scheduleRepo.save(schedule);
    }
    private void newSchedule3_3(){
        Route route1 = new Route();
        route1.setId(5L);
        List<LocalTime> timeArr = new ArrayList<>();

        timeArr.add(LocalTime.of(13,50));
        timeArr.add(LocalTime.of(13,52));
        timeArr.add(LocalTime.of(13,55));
        timeArr.add(LocalTime.of(13,58));
        timeArr.add(LocalTime.of(14,0));
        timeArr.add(LocalTime.of(14,2));
        timeArr.add(LocalTime.of(14,5));
        timeArr.add(LocalTime.of(14,7));
        timeArr.add(LocalTime.of(14,9));
        timeArr.add(LocalTime.of(14,10));
        timeArr.add(LocalTime.of(14,12));
        timeArr.add(LocalTime.of(14,14));
        timeArr.add(LocalTime.of(14,16));
        timeArr.add(LocalTime.of(14,18));
        timeArr.add(LocalTime.of(14,20));
        timeArr.add(LocalTime.of(14,26));
        timeArr.add(LocalTime.of(14,28));

        Trip1Way trip2 = new Trip1Way(false, BoundFor.TWO_WAYS_OUT_BOUND, timeArr, route1);
        Schedule schedule = new Schedule("važiuoja mokslo metu", List.of(trip2));
        Line line = new Line();
        line.setId(3L);
        schedule.setLine(line);


        scheduleRepo.save(schedule);
    }
    private void newSchedule3_4(){
        Route route1 = new Route();
        route1.setId(4L);
        List<LocalTime> timeArr = new ArrayList<>();

        timeArr.add(LocalTime.of(14,30));
        timeArr.add(LocalTime.of(14,32));
        timeArr.add(LocalTime.of(14,34));
        timeArr.add(LocalTime.of(14,36));
        timeArr.add(LocalTime.of(14,38));
        timeArr.add(LocalTime.of(14,40));
        timeArr.add(LocalTime.of(14,42));
        timeArr.add(LocalTime.of(14,44));
        timeArr.add(LocalTime.of(14,46));
        timeArr.add(LocalTime.of(14,48));
        timeArr.add(LocalTime.of(14,50));
        timeArr.add(LocalTime.of(14,52));
        timeArr.add(LocalTime.of(14,54));
        timeArr.add(LocalTime.of(14,56));
        timeArr.add(LocalTime.of(14,58));
        timeArr.add(LocalTime.of(15,0));
        timeArr.add(LocalTime.of(15,3));
        timeArr.add(LocalTime.of(15,6));
        timeArr.add(LocalTime.of(15,8));

        Trip1Way trip1 = new Trip1Way(false, BoundFor.TWO_WAYS_CITY_BOUND, timeArr, route1);


//==============

        List<LocalTime> timeArr2 = new ArrayList<>();

        timeArr2.add(LocalTime.of(16,0));
        timeArr2.add(LocalTime.of(16,2));
        timeArr2.add(LocalTime.of(16,3));
        timeArr2.add(LocalTime.of(16,5));
        timeArr2.add(LocalTime.of(16,7));
        timeArr2.add(LocalTime.of(16,9));
        timeArr2.add(LocalTime.of(16,11));
        timeArr2.add(LocalTime.of(16,13));
        timeArr2.add(LocalTime.of(16,19));
        timeArr2.add(LocalTime.of(16,20));
        timeArr2.add(LocalTime.of(16,24));
        timeArr2.add(LocalTime.of(16,25));
        timeArr2.add(LocalTime.of(16,27));
        timeArr2.add(LocalTime.of(16,29));
        timeArr2.add(LocalTime.of(16,30));
        timeArr2.add(LocalTime.of(16,31));
        timeArr2.add(LocalTime.of(16,32));
        timeArr2.add(LocalTime.of(16,33));
        timeArr2.add(LocalTime.of(16,35));

        Trip1Way trip2 = new Trip1Way(true, BoundFor.TWO_WAYS_OUT_BOUND, timeArr, route1);
        Schedule schedule = new Schedule("važiuoja mokslo metu", List.of(trip1, trip2));
        Line line = new Line();
        line.setId(3L);
        schedule.setLine(line);
        scheduleRepo.save(schedule);
    }
    private void newSchedule3_6(){
        Route route1 = new Route();
        route1.setId(5L);
        List<LocalTime> timeArr = new ArrayList<>();

        timeArr.add(LocalTime.of(16,35));
        timeArr.add(LocalTime.of(16,37));
        timeArr.add(LocalTime.of(16,40));
        timeArr.add(LocalTime.of(16,41));
        timeArr.add(LocalTime.of(16,44));
        timeArr.add(LocalTime.of(16,46));
        timeArr.add(LocalTime.of(16,50));
        timeArr.add(LocalTime.of(16,53));
        timeArr.add(LocalTime.of(16,55));
        timeArr.add(LocalTime.of(17,0));
        timeArr.add(LocalTime.of(17,2));
        timeArr.add(LocalTime.of(17,5));
        timeArr.add(LocalTime.of(17,6));
        timeArr.add(LocalTime.of(17,7));
        timeArr.add(LocalTime.of(17,8));
        timeArr.add(LocalTime.of(17,9));
        timeArr.add(LocalTime.of(17,10));


        Trip1Way trip1 = new Trip1Way(true, BoundFor.TWO_WAYS_CITY_BOUND, timeArr, route1);
        Schedule schedule = new Schedule("važiuoja darbo dienomis", List.of(trip1));
        Line line = new Line();
        line.setId(3L);
        schedule.setLine(line);

        scheduleRepo.save(schedule);
    }
    //</editor-fold>
}