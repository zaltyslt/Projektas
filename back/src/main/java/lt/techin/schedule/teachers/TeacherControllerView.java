package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.SubjectService;
import lt.techin.schedule.teachers.contacts.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/teachers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TeacherControllerView {
    private final TeacherServiceFind teacherFinder;
    private final ContactService contactService;
    private final SubjectService subjectService;
    private final TeacherRepository teacherRepository;
    @Autowired
    public TeacherControllerView(TeacherServiceFind teacherFinder, ContactService contactService, SubjectService subjectService, TeacherRepository teacherRepository) {
        this.teacherFinder = teacherFinder;
        this.contactService = contactService;
        this.subjectService = subjectService;
        this.teacherRepository = teacherRepository;
    }

//    @GetMapping
//    @ResponseBody
//    public ResponseEntity<Set<TeacherDto>> getAllTeachers(@RequestParam(value = "active", required = false) Optional<Boolean> isActive) {
//
//
//        var result = teacherFinder.getAllTeachers(isActive);
//        return !result.isEmpty()
//                ? ResponseEntity.ok(result)
//                : ResponseEntity.noContent().build();
//    }


    @GetMapping
    @ResponseBody
    public Set<TeacherDto> getAllTeachers(@RequestParam(value = "active", required = false) Optional<Boolean> active) {
        Set<TeacherDto> teachers; //Fixme Dev remove
        if (active.isPresent()) {
            teachers = teacherFinder.getTeachersByActiveStatus(active.get());
        } else {
            teachers = teacherFinder.getAllTeachers();
        }
    return  teachers;

    }

    @GetMapping(value = "/view") //view by teacherID via path
    public ResponseEntity<TeacherDto> getTeacherById(@RequestParam(value = "tid", required = false) Long teacherId) {
        var result =  teacherFinder.getTeacherById(teacherId);

        return result.getId() != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();

    }
}
