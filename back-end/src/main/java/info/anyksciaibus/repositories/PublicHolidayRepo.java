package info.anyksciaibus.repositories;

import info.anyksciaibus.entities.timeconstraints.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicHolidayRepo extends JpaRepository<PublicHoliday, Long> {
}
