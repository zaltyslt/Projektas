package lt.techin.schedule.schedules.planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
    List<WorkDay> findWorkDaysByScheduleId(Long scheduleId);

    long deleteBySubjectId(Long subjectId);
}
