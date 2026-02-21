package info.anyksciaibus.services.timeconstraints;

import info.anyksciaibus.entities.timeconstraints.PublicHoliday;
import info.anyksciaibus.repositories.PublicHolidayRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PublicHolidayService {


    PublicHolidayRepo repo;

    public PublicHolidayService(PublicHolidayRepo repo){
        this.repo = repo;
    }


    public List<PublicHoliday> getAll() {
        return repo.findAll();
    }

    public Optional<PublicHoliday> get1LineById(Long id) {
        return repo.findById(id);
    }

    public List<PublicHoliday> saveAll(List<PublicHoliday> holidayList) {
        return repo.saveAll(holidayList);
    }

    public PublicHoliday save1(PublicHoliday holiday) {
        return repo.save(holiday);
    }

    public void delete1byId(Long id) {
        repo.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        repo.deleteAllById(ids);
    }


    public boolean isTheDayPublicHoliday(LocalDate date){
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();

        List<PublicHoliday> publicHolidays = repo.findAll();

        for (PublicHoliday holiday : publicHolidays) {
            if (month == holiday.getMonth() && dayOfMonth == holiday.getDay())
                return true;
        }

        LocalDate easterDate = calculateEasterDate(date.getYear());


        return date.isEqual(easterDate) ||
                date.isEqual(easterDate.plusDays(1));

    }

    public static LocalDate calculateEasterDate(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(year, month, day);
    }



}
