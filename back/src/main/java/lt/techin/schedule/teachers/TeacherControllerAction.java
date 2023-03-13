package lt.techin.schedule.teachers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
//        return ResponseEntity.ok(teacherDto);
        if (
//                result.getSubjectsList() != null &&
                result.getContacts() != null &&
                result.getSelectedShift() != null &&
//                result.getWorkHoursPerWeek() != null &&
                result.getActive() != null &&
                result.getSelectedShift() != null
        ){
            return teacherServiceDo.createTeacher(result);
        }
        throw new TeacherException(HttpStatus.EXPECTATION_FAILED,"Nepakanka duomenų!");

    }
    @PostMapping(value = "/create/test", consumes = {MediaType.APPLICATION_JSON_VALUE}) //teacher create   sdf
//public String testData(@RequestBody String payLoad) {
    public String testData(@RequestBody String payLoad) {
        var aa = payLoad;
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = new Object();
        Exception ee;
        try {
       jsonObject = objectMapper.readValue(payLoad, TeacherDto.class);
//       jsonObject = objectMapper.readValue(payLoad, TeacherSubjectsDto.class);
            var res = objectMapper.writeValueAsString(jsonObject);
        } catch (
                Exception e) {

        }

//       return !payLoad.isEmpty()
        return payLoad != null
                ? "OK"
                : "Not OK";
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherDto> updateTeacherDetails(@RequestParam("tid") Long teacherId, @RequestBody TeacherDto teacherDto) {
        return teacherServiceDo.updateTeacher(teacherId, teacherDto);
    }

    @PatchMapping("/active")
    public ResponseEntity<Void> updateTeacherDetails(@RequestParam("tid") Long teacherId, @RequestParam("active") boolean status) {
        return status
                ? teacherServiceDo.switchOn(teacherId)
                : teacherServiceDo.switchOff(teacherId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTeacherDetails(@RequestParam("tid") Long teacherId) {
        return teacherServiceDo.deleteTeacherById(teacherId);
    }

}