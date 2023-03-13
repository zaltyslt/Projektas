package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.SubjectService;
import lt.techin.schedule.teachers.contacts.ContactService;
import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/teachers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TeacherControllerView {
    private final TeacherServiceFind teacherFinder;
    //    private final ContactService contactService;
//    private final SubjectService subjectService;
//    private final TeacherRepository teacherRepository;

    public TeacherControllerView(TeacherServiceFind teacherFinder) {
        this.teacherFinder = teacherFinder;
    }

    @GetMapping
    @ResponseBody
    public List<TeacherDto> getAllTeachers(@RequestParam(value = "active", required = false) Optional<Boolean> active) {
        List<TeacherDto> teachers; //Fixme Dev remove
        if (active.isPresent()) {
            teachers = teacherFinder.getTeachersByActiveStatus(active.get());
        } else {
            teachers = teacherFinder.getAllTeachers();
        }
        return teachers;

    }

    @GetMapping(value = "/view") //view by teacherID via params
    public ResponseEntity<TeacherDto> getTeacherById(@RequestParam(value = "tid", required = true) Long teacherId) {
        var result = teacherFinder.getTeacherById(teacherId);

        return result.getId() != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();

    }

    @GetMapping(value = "/subjects") //get
    public ResponseEntity<Set<TeacherSubjectsDto>> getActiveTeacherSubjectsDto() {
        var result = teacherFinder.getMiniSubjects();

        return ResponseEntity.ok(result);

    }

    @GetMapping(value = "/subject")
    public List<TeacherEntityDto> getTeachersBySubject(@RequestParam Long subjectId, @RequestParam Long shiftId) {
        return teacherFinder.findTeachersBySubjectsId(subjectId, shiftId).stream().map(TeacherMapper::toTeacherEntityDto).collect(Collectors.toList());
    }
}
