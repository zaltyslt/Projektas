package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.SubjectService;
import lt.techin.schedule.teachers.contacts.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@Service
public class TeacherControllerView {
    TeacherService teacherService;
    ContactService contactService;
    SubjectService subjectService;
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherControllerView(TeacherService teacherService, ContactService contactService, SubjectService subjectService,
                                 TeacherRepository teacherRepository) {
        this.teacherService = teacherService;
        this.contactService = contactService;
        this.subjectService = subjectService;
        this.teacherRepository = teacherRepository;
    }

    public Set<TeacherDto> getAllTeachers(Optional<Boolean> active) {
        Set<TeacherDto> teachers; //Fixme Dev remove
        if (active.isPresent()) {
            teachers = teacherService.getTeachersByActiveStatus(active.get());
        } else {
            teachers = teacherService.getAllTeachers();
        }
    return  teachers;

    }

    public TeacherDto getTeacherById(Long teacherId) {
        var result =  teacherService.getTeacherById(teacherId);

        return result;

    }
}
