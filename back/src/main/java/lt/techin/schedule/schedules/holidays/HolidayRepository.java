package lt.techin.schedule.schedules.holidays;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    @Query(value = "SELECT * FROM HOLIDAY WHERE SCHEDULE_ID = ?", nativeQuery = true)
    List<Holiday> findByScheduleId(Long id);
}
