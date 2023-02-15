package lt.techin.schedule.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByModuleId(Long moduleId);

//    List<Subject> findSubjectsByClassRoomId(Long classRoomId);

}
