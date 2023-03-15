package lt.techin.schedule.classrooms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Page<Classroom> findByClassroomName
            (String classroomName, Pageable pageable);
}
