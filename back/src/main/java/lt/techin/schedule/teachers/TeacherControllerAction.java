package lt.techin.schedule.teachers;

import lt.techin.schedule.exceptions.TeacherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/teachers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TeacherControllerAction {
    private TeacherServiceDo teacherServiceDo;

    @Autowired
    public TeacherControllerAction(TeacherServiceDo teacherServiceDo) {
        this.teacherServiceDo = teacherServiceDo;
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE}) //teacher create
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
        var result = teacherDto;
        if (
                result.getSelectedShift() != null &&
                        result.getfName() != null &&
                        result.getContacts() != null &&
                        result.getSelectedShift() != null &&
                        result.getActive() != null
        ) {
            return teacherServiceDo.createTeacher(result);
        } else {
            throw new TeacherException(HttpStatus.EXPECTATION_FAILED, "Nepakanka duomenų!");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherDto> updateTeacherDetails(@RequestParam("tid") Long teacherId,
                                                           @RequestBody TeacherDto teacherDto) {
        return teacherServiceDo.updateTeacher(teacherId, teacherDto);
    }

    @PatchMapping("/active")
    public ResponseEntity<Void> changeState(@RequestParam("tid") Long teacherId, @RequestParam("active") boolean status) {
        return status
                ? teacherServiceDo.switchOn(teacherId)
                : teacherServiceDo.switchOff(teacherId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTeacherDetails(@RequestParam("tid") Long teacherId) {
        return teacherServiceDo.deleteTeacherById(teacherId);
    }
}