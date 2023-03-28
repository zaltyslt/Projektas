package lt.techin.schedule.schedules.holidays.HolidayPlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayPlanRepository extends JpaRepository<HolidayPlan, Long> {
    @Query(value = "SELECT * FROM HOLIDAY_PLAN WHERE SCHEDULE_ID = ?", nativeQuery = true)
    List<HolidayPlan> findByScheduleId(Long id);
}

