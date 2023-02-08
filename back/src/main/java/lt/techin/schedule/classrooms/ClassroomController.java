package lt.techin.schedule.classrooms;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
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

    @GetMapping(value = "/classroom", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ClassroomDto> getClassroom() {
        var response = new ClassroomDto();
        return ResponseEntity.ok(response);
    }

//    @GetMapping(value = "/classrooms", produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ClassroomDto> getClassrooms() {
        return classroomService.getAll().stream().map(ClassroomMapper::toClassroomDto).collect(toList());
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody ClassroomDto classroomDto) {
        var createClassroom = classroomService.create(toClassroom(classroomDto));
        return ok(toClassroomDto(createClassroom));
    }

    @PatchMapping("/update/{classroomId}")
    public ResponseEntity<ClassroomDto> updateClassroom(@PathVariable Long classroomId,
                                                        @RequestBody ClassroomDto classroomDto) {
        var updatedClassroom = classroomService.update(classroomId, toClassroom(classroomDto));
        return ok(toClassroomDto(updatedClassroom));
    }

//    @DeleteMapping("/delete/{classroomId}")
//    public ResponseEntity<Void> deleteClassroom(@PathVariable Long classroomId) {
//        boolean deleted = classroomService.deleteById(classroomId);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("/addBuilding/{classroomId}")
    @ResponseBody
    public Classroom addBuildingToClassroom(@PathVariable Long classroomId, @RequestParam Long buildingId) {
        return classroomService.addClassroomToBuilding(classroomId, buildingId);
    }
}
