package trash;

import lt.techin.schedule.module.ModuleRepository;
import lt.techin.schedule.shift.ShiftDTO;
import lt.techin.schedule.subject.SubjectEntityDto;
import lt.techin.schedule.subject.SubjectMapper;
import lt.techin.schedule.teachers.contacts.ContactService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

//@RestController
//@RequestMapping(value = "/api/v1/teachers", produces = {MediaType.APPLICATION_JSON_VALUE})
//@RequestMapping(value = "/api/v1/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherController {

    private final ContactService contactService;
    private final TeacherService teacherService;
    private final TeacherControllerAction action;
    private final ShiftDTO repoShift;
    private  final ModuleRepository repoModule;

    public static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private final TeacherRepository teacherRepository;

    public TeacherController(ContactService contactService, TeacherService teacherService, TeacherControllerAction action, ShiftDTO repoShift, ModuleRepository repoModule, TeacherRepository teacherRepository) {
        this.contactService = contactService;
        this.teacherService = teacherService;
        this.action = action;
        this.repoShift = repoShift;
        this.repoModule = repoModule;
        this.teacherRepository = teacherRepository;
    }



//    @GetMapping(value = "/view") //view by teacherID via path
//    public ResponseEntity<TeacherDto> getTeacherById(@RequestParam(value = "tid", required = false) Long teacherId) {
//        var result =  finder.getTeacherById(teacherId);
//
//        return result.getId() != null
//                ? ResponseEntity.ok(result)
//                : ResponseEntity.notFound().build();
//
//    }

//    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE}) //teacher create
//    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
//       var newTeacherDto = action.createTeacher(teacherDto);
//
//        return newTeacherDto.getId() != null
//                ? ResponseEntity.ok(newTeacherDto)
//                : ResponseEntity.badRequest().build();
//    }

//    @PutMapping("/update")
//    public ResponseEntity<TeacherDto> updateTeacherDetails(@RequestParam("tid") Long teacherId, @RequestBody TeacherDto teacherDto) {
//        var updatedTeacher = teacherService.updateTeacher(teacherId, teacherDto);
////
//        return ok(updatedTeacher);
//    }

    @PutMapping("/update/subjects")
    public ResponseEntity<String> updateTeacherSubjects(@RequestParam("tid") Long teacherId, @RequestBody List<SubjectEntityDto> subjectsDto) {
//    public ResponseEntity<String> updateTeacherSubjects(@RequestParam("tid") Long teacherId, @RequestBody List<TeacherSubjectsDto> subjectsDto) {
        var request = subjectsDto;

        var updateStatus = teacherService.updateTeacherSubjects(teacherId, subjectsDto);

        return updateStatus ?   ResponseEntity.ok("Dalykai išsaugoti sėkmingai.")
                : ResponseEntity.internalServerError().body( "Dalykų išsaugoti nepavyko !!!");

//        var teacher = teacherRepository.findById(1L).get();
//        return ResponseEntity.ok(TeacherSubjectMapper.subjectsToDtos(teacher.getSubjects()));
    }
@GetMapping("/update/subjects")//For DEV
    public ResponseEntity<List<SubjectEntityDto>> showTeacherSubjects(@RequestParam("tid") Long teacherId) {
//    public ResponseEntity<List<TeacherSubjectsDto>> showTeacherSubjects(@RequestParam("tid") Long teacherId) {
        var teacher = teacherRepository.findById(1L).get();
var ssss = teacher.getSubjects().stream().map(s->SubjectMapper.toSubjectEntityDto(s)).toList();
        return ResponseEntity.ok(ssss);
//        return ResponseEntity.ok(TeacherSubjectMapper.subjectsToDtos(teacher.getSubjects()));
    }

    @PatchMapping("/active")
    public ResponseEntity<String> changeActiveStatus(@RequestParam("tid") Long teacherId, @RequestParam("active") Boolean active) {

        var message = teacherService.switchTeacherisActive(teacherId, active);
        return ResponseEntity.ok("teacher id: " + teacherId + ": " + message);
    }

}


//    @GetMapping()
//    @ResponseBody
//    public List<Teacher> findTeacherByName(@RequestParam String name) {
//
//        return teacherService.getTeachersByName(name);
//
//    }


