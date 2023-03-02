package lt.techin.schedule.programs.subjectsHours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectHoursRepository extends JpaRepository<SubjectHours, Long> {
}
