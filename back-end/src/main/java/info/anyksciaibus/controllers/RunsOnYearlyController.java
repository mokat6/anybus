package info.anyksciaibus.controllers;

import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;
import info.anyksciaibus.services.timeconstraints.RunsOnYearlyService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/yearly-rules")
public class RunsOnYearlyController {
    RunsOnYearlyService service;

    public RunsOnYearlyController(RunsOnYearlyService service){
        this.service = service;
    }

    @GetMapping(value = "/rules", produces = "text/html")
    public String getPassingYearlyRules(@RequestParam String dateStr){
        LocalDate date;
        try{
            date = LocalDate.parse(dateStr);
        }catch (DateTimeParseException e){
            return "incorrect date format. try yyyy-mm-dd e.g. 2024-12-07";
        }
        return service.passingYearlyRulesByDate(date).toString();
    }

    @GetMapping("/get-all")
    public Map<String, Object> getAllRules(){
        List<RunsOnYearly> allRules = service.getAll();

//
//        Map<TypeOfYearlyRule, List<RunsOnYearly>> rulesMap = new HashMap<>();
//        for (TypeOfYearlyRule type : TypeOfYearlyRule.values()) {
//            rulesMap.put(type, new ArrayList<>());
//        }
//
//        Map<TypeOfYearlyRule, List<RunsOnYearly>> rules =  allRules.stream()
//                .collect(Collectors.groupingBy(RunsOnYearly::getTypeOfYearlyRule));
//
//        rulesMap.putAll(rules);

        return Map.of
                (
                "rules", allRules,
                "schedules", service.findScheduleIdsByRunsOnYearly(allRules)
                );
    }

    @PostMapping("/post-combo-list")
    public List<RunsOnYearly> postListOfYearlyRules(@RequestBody List<RunsOnYearly> list) {
//    public Map<TypeOfYearlyRule, List<RunsOnYearly>> postListOfYearlyRules(@RequestBody List<RunsOnYearly> list) {
//
//        Map<TypeOfYearlyRule, List<RunsOnYearly>> rulesMap = new HashMap<>();
//        for (TypeOfYearlyRule type : TypeOfYearlyRule.values()) {
//            rulesMap.put(type, new ArrayList<>());
//        }
//
//        Map<TypeOfYearlyRule, List<RunsOnYearly>> rules = service.saveAll(list).stream()
//                .collect(Collectors.groupingBy(RunsOnYearly::getTypeOfYearlyRule));
//
//        rulesMap.putAll(rules);
//        return rulesMap;
        return service.saveAll(list);
    }

}
