package info.anyksciaibus.services.timeconstraints;


import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;
import info.anyksciaibus.entities.timeconstraints.TimePeriod;
import info.anyksciaibus.entities.timeconstraints.TypeOfYearlyRule;
import info.anyksciaibus.repositories.RunsOnYearlyRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RunsOnYearlyService {
    RunsOnYearlyRepo repo;


    public RunsOnYearlyService(RunsOnYearlyRepo repo){
        this.repo = repo;
    }

    public List<RunsOnYearly> getAll() {
        return repo.findAll();
    }

    public Optional<RunsOnYearly> get1RunsOnYearlyById(Long id) {
        return repo.findById(id);
    }

    public List<RunsOnYearly> saveAll(List<RunsOnYearly> updatedRules) {
        List<RunsOnYearly> allRulesInDB = repo.findAll();

        List<RunsOnYearly> rulesToDelete = new ArrayList<>(allRulesInDB);
        rulesToDelete.removeAll(updatedRules);
        for (RunsOnYearly rule : rulesToDelete) {
            if (repo.existsSchedulesByRunsOnYearly(rule))
                rulesToDelete.remove(rule);
        }

        repo.deleteAll(rulesToDelete);

        //without it server 500 error - tried persisting with duplicate key ..
        updatedRules.forEach(rule -> {
            if (rule.getCopyTimePeriodsFromOtherRule() == null) return;

            Long id = rule.getCopyTimePeriodsFromOtherRule().getId();
            if (id != null) {
                Optional<RunsOnYearly> optionalCopy = repo.findById(id);
                if (optionalCopy.isPresent())
                    rule.setCopyTimePeriodsFromOtherRule(optionalCopy.get());
            }
            else
                rule.setCopyTimePeriodsFromOtherRule(null);
        });



        return repo.saveAll(updatedRules);
    }

    public RunsOnYearly save1(RunsOnYearly runsOnYearly) {
        return repo.save(runsOnYearly);
    }

    public void delete1byId(Long id) {
        repo.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        repo.deleteAllById(ids);
    }

    public List<RunsOnYearly> passingYearlyRulesByDate(LocalDate date){

        List<RunsOnYearly> allRules = repo.findAll();
        return allRules.stream().filter(rule -> {
            rule.updateFromDateRangeRefs();

            //check FIXED_DATES first, is the date within date ranges? if no date ranges - skip
            List<TimePeriod> dateRanges = rule.getTimePeriods();

            boolean passedStaticDateRange = true;
            if (!dateRanges.isEmpty()) {
                passedStaticDateRange = false;

                for (TimePeriod d : dateRanges) {
                    LocalDate start = LocalDate.of(date.getYear(),d.getStartMonth(), d.getStartDay());
                    LocalDate end = LocalDate.of(date.getYear(),d.getEndMonth(), d.getEndDay());

                    if( (date.isAfter(start) || date.isEqual(start)) &&
                        (date.isBefore(end) || date.isEqual(end))  ) {
                        passedStaticDateRange = true;
                        break;
                    }
                }
            }

            if (!passedStaticDateRange) return false;

            //FIXED_DATES passes (or non existent) then
            //check if passes FORMULA_PATTERN. If doesn't exist, auto pass
            return rule.testPattern1(date);

        }).collect(Collectors.toList());



    }

    public List<RunsOnYearly> getAllRulesGroupedByType(TypeOfYearlyRule type){
        return repo.findByTypeOfYearlyRule(type);
    }

    public List<Map<String, Object>> findScheduleIdsByRunsOnYearly(List<RunsOnYearly> runsOnYearlyList) {
     return    runsOnYearlyList.stream().map(roy ->
           //map: key = RunsOnYearly id, value = List of schedules that use this rule
             Map.of(
                   "ruleId", roy.getId() ,
                   "schedules" ,repo.findScheduleIdsByRunsOnYearly(roy)
        )).toList();
    }

}
