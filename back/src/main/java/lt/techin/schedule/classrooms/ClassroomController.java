package lt.techin.schedule.classrooms;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroom;
import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroomDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping(value = "/classroom/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClassroomDto> getClassroom(@PathVariable Long id) {
        return ResponseEntity.ok(ClassroomMapper.toClassroomDto(classroomService.finById(id)));
    }

    //    @GetMapping(value = "/classrooms", produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ClassroomDto> getClassrooms() {
        return classroomService.getAll().stream().map(ClassroomMapper::toClassroomDto).toList();
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody ClassroomDto classroomDto) {
        var createClassroom = classroomService.create(toClassroom(classroomDto));
        if (createClassroom == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toClassroomDto(createClassroom));
    }

    @PatchMapping("/update/{classroomId}")
    public ResponseEntity<ClassroomDto> updateClassroom(@PathVariable Long classroomId,
                                                        @RequestBody ClassroomDto classroomDto) {
        var updatedClassroom = classroomService.update(classroomId, toClassroom(classroomDto));
        return ok(toClassroomDto(updatedClassroom));
    }

    @PatchMapping("/disable/{classroomId}")
    public ClassroomDto disableClassroom(@PathVariable Long classroomId) {
        var disableClassroom = classroomService.disable(classroomId);
        return toClassroomDto(disableClassroom);
    }

    @PatchMapping("/enable/{classroomId}")
    public ClassroomDto enableClassroom(@PathVariable Long classroomId) {
        var disableClassroom = classroomService.enable(classroomId);
        return toClassroomDto(disableClassroom);
    }
}
