package info.anyksciaibus.services;

import jakarta.transaction.Transactional;
import info.anyksciaibus.dto.*;
import info.anyksciaibus.dto.scheduling.ScheduleDto;
import info.anyksciaibus.dto.scheduling.Trip1WayIdDto;
import info.anyksciaibus.entities.*;
import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;
import info.anyksciaibus.repositories.ScheduleRepo;
import info.anyksciaibus.services.timeconstraints.PublicHolidayService;
import info.anyksciaibus.services.timeconstraints.RunsOnYearlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {


    ScheduleRepo scheduleRepo;

    //LinkedHashMap to maintain the insertion order of the entries
    private static final int CACHE_SIZE_LIMIT = 10;
    private Map<LocalDate, DatePropertiesRecord> datePropertiesCache = new LinkedHashMap<LocalDate, DatePropertiesRecord>(CACHE_SIZE_LIMIT, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<LocalDate, DatePropertiesRecord> eldest) {
            return size() > CACHE_SIZE_LIMIT;
        }
    };


    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }


    public List<Schedule> getAll() {
        return scheduleRepo.findAll();
    }

    public Optional<Schedule> get1ScheduleById(Long id) {
        return scheduleRepo.findById(id);
    }

    public List<Schedule> saveAll(List<Schedule> scheduleList) {
        return scheduleRepo.saveAll(scheduleList);
    }

    public Schedule save1(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    public void delete1byId(Long id) {
        scheduleRepo.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        scheduleRepo.deleteAllById(ids);
    }


    //===============================================================
    public SingleTrip getSingleTrip(Long id) {
        Optional<Schedule> singleEntityOptional = scheduleRepo.findById(id);

        if (singleEntityOptional.isEmpty()) return new SingleTrip();

        Schedule schedule = singleEntityOptional.get();
        Line line = schedule.getLine();
//
//        List<LocalTime> tp = schedule.getTimeList();
//        List<BusStop> bs = schedule.getRoute().getStopsArr();
//
//        if (schedule.getRouteDirReversed())
//            Collections.reverse(bs);
//
//        List<SingleStop> stopsList = new ArrayList<>();
//        for (int i = 0; i < schedule.getRoute().getStopsArr().size(); i++) {
//            stopsList.add(new SingleStop(
//                    bs.get(i).getId(), tp.get(i).toString(), bs.get(i).getName())
//            );
//        }
//
//        return new SingleTrip(
//                line.getName(),
//                line.getRouteStart(),
//                line.getRouteEnd(),
//                line.getOperator(),
//                line.getAnykStationPlatform(),
//                line.getPrice(),
//                line.getRouteType().toString(),
//                schedule.getTimeConstraintsDescription(),
//                stopsList
//        );
        return null;
    }

    @Autowired
    PublicHolidayService publicHolidayService;
    @Autowired
    RunsOnYearlyService runsOnYearlyService;

    public List<Trip1WayHomeDto> getScheduleItemsHome(LocalDate date, BoundFor boundFor, Long qbstopfrom, Long qbstopto) {
        if (date == null || boundFor == null || qbstopfrom == null) return List.of(new Trip1WayHomeDto());

        DayOfWeek dayOfWeek = date.getDayOfWeek();;

        boolean isPublicHoliday;
        List<RunsOnYearly> runsOnYearlyList;

        DatePropertiesRecord cacheRec = datePropertiesCache.get(date);
        if (cacheRec == null) {
            isPublicHoliday = publicHolidayService.isTheDayPublicHoliday(date);
            runsOnYearlyList = runsOnYearlyService.passingYearlyRulesByDate(date);

            DatePropertiesRecord cacheRecord = new DatePropertiesRecord(isPublicHoliday, runsOnYearlyList);
            datePropertiesCache.put(date, cacheRecord);

        } else {
            isPublicHoliday = cacheRec.isPublicHoliday();
            runsOnYearlyList = cacheRec.runsOnYearlyList();
        }

        List<Trip1Way> foundTrips;
        //qbstopfrom = 1L is the main station of the city.  main station + CITY_BOUND makes no sense, so it is used for inner city buses
        if (qbstopfrom.equals(1L) && qbstopto != null && boundFor == BoundFor.TWO_WAYS_OUT_BOUND) {
            foundTrips = scheduleRepo.findTripsByConditionsToAndFrom(runsOnYearlyList, dayOfWeek, boundFor, qbstopfrom, isPublicHoliday ? true : null, qbstopto);
        }
        else if (qbstopfrom.equals(1L)  && boundFor == BoundFor.TWO_WAYS_CITY_BOUND)
            foundTrips = scheduleRepo.findTripsCityBus(runsOnYearlyList, dayOfWeek, qbstopfrom, isPublicHoliday ? true : null);
        else if (qbstopfrom.equals(1L) && boundFor == BoundFor.TWO_WAYS_OUT_BOUND)
            foundTrips = scheduleRepo.findTripsByConditionsNotCityBus(runsOnYearlyList, dayOfWeek, boundFor ,qbstopfrom, isPublicHoliday ? true : null);
        else
            foundTrips = scheduleRepo.findTripsByConditions(runsOnYearlyList, dayOfWeek, boundFor ,qbstopfrom, isPublicHoliday ? true : null);


        return foundTrips.stream().flatMap(trip -> {
            List<Trip1WayHomeDto> dtoList = Trip1WayHomeDto.tripToDtoList(trip, qbstopfrom);

            return dtoList.stream();
        }).toList();
    }
    //=================

    public Map<LocalDate, DatePropertiesRecord> getDatePropertiesCache() {
        return datePropertiesCache;
    }

@Transactional
    public List<Schedule> getScheduleByLine (Long lineId){
        List<Schedule> schedules = scheduleRepo.findByLineId(lineId);

//    schedules.forEach(x-> {
//        x.getTrips().forEach(trip -> trip.getRoute().setDistanceMetersList(null));
//    });
        return schedules;
    }

    public String findLineNameByLineId(Long id){
        Optional<String> nameOptional = scheduleRepo.getLineTitleByLineId(id);
        if (nameOptional.isPresent())
            return nameOptional.get();
        else return "name not found";
    }


    public List<Long> getScheduleIdsByRoute(Route route){
        return scheduleRepo.findScheduleIdsByRoute(route);
    }

    public ScheduleDto getEmptyDto(){
        ScheduleDto dto = ScheduleDto.scheduleToDto(new Schedule());
        Trip1WayIdDto tripDto = new Trip1WayIdDto();
        tripDto.setBoundFor(BoundFor.values()[0]);
        tripDto.setRouteDirReversed(false);
        tripDto.setTimeList(Collections.emptyList());
        dto.setTrips(List.of(tripDto));

        return dto;

    }

    public List<Map<String, Object>>  findByRunsOnYearlyIdGroupByLine(Long id){

        List<Schedule> schedules = scheduleRepo.findByRunsOnYearlyId(id);

        Map<LineInfo, List<ScheduleByYearlyRuleDto>> groupedMap = schedules.stream().map(ScheduleByYearlyRuleDto::ScheduleToDto)
                .collect(Collectors.groupingBy(ScheduleByYearlyRuleDto::getLineInfo));

        List<Map<String, Object>> resultList = groupedMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("line", entry.getKey());
                    resultMap.put("schedules", entry.getValue());
                    return resultMap;
                })
                .collect(Collectors.toList());

        return resultList;


    }

    //single trip page

    public Map<String, ?> getSCheduleByTripInfo(Long id) {
        Schedule schedule = scheduleRepo.findByTripId(id).orElse(new Schedule());

        return Map.of(
                "schedule", schedule,
                "tripId", id,
                "lineInfo", LineInfo.LineToDto(schedule.getLine()));
    }

}

