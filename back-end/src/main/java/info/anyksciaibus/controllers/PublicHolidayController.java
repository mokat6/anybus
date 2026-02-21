package info.anyksciaibus.controllers;

import info.anyksciaibus.entities.timeconstraints.PublicHoliday;
import info.anyksciaibus.services.timeconstraints.PublicHolidayService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PublicHolidayController {

    PublicHolidayService service;

    public PublicHolidayController(PublicHolidayService service){
        this.service = service;
    }


    @GetMapping(value = "/checkIfHoliday", produces = "text/html")
    public String checkIfItsHoliday(@RequestParam String dateStr){
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        }catch (DateTimeParseException e){
            return "the date cannot be parsed. Please prodive yyyy-mm-dd<br> e.g. 2024-05-02";
        }

        return service.isTheDayPublicHoliday(date) ? "It is a public holiday" : "no - go to work!";


    }
    @GetMapping("/all")
    public List<PublicHoliday> getAllHolidays(){
        return service.getAll();
    }

    @PostMapping("/save1")
    public PublicHoliday saveOneHoliday(@RequestBody PublicHoliday holiday){
        return service.save1(holiday);
    }

    @DeleteMapping("/del1")
    public void deleteOneHoliday(@RequestParam Long id){
        service.delete1byId(id);
    }

}
