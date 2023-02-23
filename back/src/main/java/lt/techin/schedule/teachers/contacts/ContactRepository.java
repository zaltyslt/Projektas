package lt.techin.schedule.teachers.contacts;

import jakarta.transaction.Transactional;
import lt.techin.schedule.teachers.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Transactional
    void deleteAllByTeacher(Teacher teacher);
    List<Contact> findContactsByTeacher(Teacher teacher);
    Optional<Contact> findContactByTeacherAndContactType(Teacher teacher, ContactType contactType );

}
