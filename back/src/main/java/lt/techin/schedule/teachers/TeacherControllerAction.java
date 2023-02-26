package lt.techin.schedule.teachers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
        return teacherServiceDo.createTeacher(result);
    }

    @PatchMapping("/update")
    public ResponseEntity<TeacherDto> updateTeacherDetails(@RequestParam("tid") Long teacherId, @RequestBody TeacherDto teacherDto) {
        return teacherServiceDo.updateTeacher(teacherId, teacherDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTeacherDetails(@RequestParam("tid") Long teacherId) {
        return teacherServiceDo.deleteTeacherById(teacherId);
    }

}