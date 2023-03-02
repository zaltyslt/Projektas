package lt.techin.schedule.teachers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return teacherServiceDo.createTeacher(result);
    }

    @PostMapping(value = "/create/test", consumes = {MediaType.APPLICATION_JSON_VALUE}) //teacher create

//public String testData(@RequestBody String payLoad) {
    public String testData(@RequestBody TeacherDto payLoad) {
var aa = payLoad;
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = new Object();
       Exception ee;
       try{
//       jsonObject = objectMapper.readValue(payLoad, Object.class);
//       jsonObject = objectMapper.readValue(payLoad, TeacherSubjectsDto.class);
       var res=  objectMapper.writeValueAsString(jsonObject);
       }catch (
               Exception e){

       }

//       return !payLoad.isEmpty()
       return payLoad != null
               ? "OK"
               : "Not OK";
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