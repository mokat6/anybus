package info.anyksciaibus.controllers;


import info.anyksciaibus.dto.*;
import info.anyksciaibus.dto.scheduling.RunsOnYearlyOptionDto;
import info.anyksciaibus.dto.scheduling.ScheduleDto;
import info.anyksciaibus.entities.BoundFor;
import info.anyksciaibus.entities.Schedule;
import info.anyksciaibus.services.ScheduleService;
import info.anyksciaibus.services.timeconstraints.RunsOnYearlyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scheduleItem")
@CrossOrigin
public class ScheduleController {


    ScheduleService service;
    RunsOnYearlyService runsOnYearlyService;

    public ScheduleController(ScheduleService service, RunsOnYearlyService runsOnYearlyService) {
        this.service = service;
        this.runsOnYearlyService = runsOnYearlyService;
    }


    @GetMapping("/get/all")
    public List<Schedule> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Schedule> getOne(@PathVariable Long id) {
        Optional<Schedule> data = service.get1ScheduleById(id);
        if (data.isPresent())
            return ResponseEntity.ok(data.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/save/multi")
    public List<Schedule> saveMulti(@RequestBody List<Schedule> list) {
        return service.saveAll(list);
    }

    @PostMapping("/save/one")
    public Schedule saveOne(@RequestBody Schedule schedule) {
        return service.save1(schedule);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOne(@PathVariable Long id) {
        service.delete1byId(id);
    }


    @DeleteMapping("/delete/multi")
    public void deleteMulti(@RequestBody List<Long> ids) {
        service.deleteMultiple(ids);
    }

    //UPSERT
    @PutMapping("/put/{id}")
    public ResponseEntity<Void> putOne(@PathVariable Long id, @RequestBody Schedule updatedSchedule) {

        Optional<Schedule> existingScheduleOptional = service.get1ScheduleById(id);
        if (existingScheduleOptional.isEmpty() || !id.equals(updatedSchedule.getId()))
            return ResponseEntity.badRequest().build();

        service.save1(updatedSchedule);
        return ResponseEntity.noContent().build();
    }

    //===================================
    @GetMapping("/home-from")
    public List<Trip1WayHomeDto> getScheduleItemsForHomeFrom(@RequestParam String qdate, String qdir, Long qbstopfrom, Long qbstopto){
        LocalDate date;
        BoundFor boundFor = BoundFor.getByDescription(qdir);
        try{
            date = LocalDate.parse(qdate);
        }catch (DateTimeParseException e){
            return null;
        }
        return service.getScheduleItemsHome(date, boundFor, qbstopfrom, qbstopto);
    }

    @GetMapping("/home-fromto")
    public List<Trip1WayHomeDto> getScheduleItemsForHomeFromTo(@RequestParam String qdate, String qdir, Long qbstopfrom, Long qbstopto){
        LocalDate date;
        BoundFor boundFor = BoundFor.getByDescription(qdir);
        try{
            date = LocalDate.parse(qdate);
        }catch (DateTimeParseException e){
            return null;
        }
        return service.getScheduleItemsHome(date, boundFor, qbstopfrom, qbstopto);
    }

    @GetMapping("/singleTrip/{id}")
    public SingleTrip getSingleTripApi(@PathVariable Long id){
        return service.getSingleTrip(id);
    }



    @GetMapping("/schedule-by-line")
    public Map<String, Object> getScheduleByLineId(@RequestParam Long lineId) {
        //Map.of()  - small up to 10, vs  -  Map.ofEntries(Map.entry("key", new Object()));

        return Map.of(
            //List<ScheduleDto>
            "data" , service.getScheduleByLine(lineId)
                        .stream().map(ScheduleDto::scheduleToDto).toList(),
            //List<BoundFor> used for select
            "boundForOptions", BoundFor.values(),
            //empty ScheduleDto
            "empty", service.getEmptyDto(),
            "yearlyOptions", runsOnYearlyService.getAll().stream()
                        .map(RunsOnYearlyOptionDto::RunsOnYearlyToDto).collect(Collectors.toList()),
            "lineTitle", service.findLineNameByLineId(lineId)
        );
    }

    //I think unused, can be deleted
    @GetMapping("/schedule-empty")
    public ScheduleDto getScheduleDtoEmpty() {
//        ScheduleDto dto = ScheduleDto.scheduleToDto(new Schedule());
        return service.getEmptyDto();
    }

    @PostMapping("/post-schedule-dto")
    public List<ScheduleDto> postScheduleDto(@RequestBody List<ScheduleDto> scheduleDtoList){
        System.out.println("hi");
        List<Schedule> response = service.saveAll(scheduleDtoList.stream()
                .map(ScheduleDto::dtoToSchedule).toList());
        return response.stream().map(ScheduleDto::scheduleToDto).toList();
    }


    @GetMapping("/get-schedules-by-rule")
    public List<Map<String, Object>> getSchedulesByRule(@RequestParam Long ruleId) {
        return service.findByRunsOnYearlyIdGroupByLine(ruleId);
    }


    //for testing only. test date constraint rules. exposes the cache Map.
//    @GetMapping("/show-cache")
//    public Map<LocalDate, DatePropertiesRecord> getCashe(){
//        return service.getDatePropertiesCache();
//    }

    @GetMapping("/schedule-by-tripid")
    public Map<String, ?> getScheduleByTripId(@RequestParam Long tripID){
        return service.getSCheduleByTripInfo(tripID);
    }

    @GetMapping("/schedule-by-lineid-browse")
    public Map<String, ?> getScheduleByLineIdBrowse(@RequestParam Long lineId){
        List<Schedule> allSchedules = service.getScheduleByLine(lineId);
        if (allSchedules.isEmpty()) return Map.of("nothingFound", true);

        return Map.of("line", LineInfo.LineToDto(allSchedules.getFirst().getLine()),
        "schedules", allSchedules.stream().map(ScheduleBrowseDto::scheduleToDto).collect(Collectors.toList()),
        "yearlyRules", allSchedules.stream().map(Schedule::getRunsOnYearly).distinct().toList()       );
    }

}

