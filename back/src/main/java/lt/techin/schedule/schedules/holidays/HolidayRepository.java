package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.planner.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

}
