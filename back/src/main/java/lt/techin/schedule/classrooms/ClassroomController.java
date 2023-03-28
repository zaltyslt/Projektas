package lt.techin.schedule.classrooms;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroom;
import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroomDto;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {
    Logger logger = Logger.getLogger(ClassroomController.class.getName());
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping(value = "/classroom/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClassroomDto> getClassroom(@PathVariable Long id) {
        logger.log(Level.INFO, "The class is rendered: {0} ", id);
        return ResponseEntity.ok(ClassroomMapper.toClassroomDto(classroomService.finById(id)));
    }

    @GetMapping(value = "/active", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ClassroomDto> getActiveClassRooms() {
        logger.info("Active classrooms");
        return classroomService.getActive().stream().map(ClassroomMapper::toClassroomDto).toList();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ClassroomDto> getClassrooms() {
        logger.info("Classes have been created");
        return classroomService.getAll().stream().map(ClassroomMapper::toClassroomDto).toList();
    }

    @PostMapping(value = "/create-classroom", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, String>> createClassroom(@RequestBody ClassroomDto classroomDto) {
        var createClassroom = classroomService.create(toClassroom(classroomDto));
        if (createClassroom == null) {
            logger.info("The class in this building is already created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tokia klasė šiame pastate jau sukurta."));
        }
        logger.info("The class was created, successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (toClassroomDto(createClassroom).toString())));
    }

    @PatchMapping("/update-classroom/{classroomId}")
    public ResponseEntity<Map<String, String>> updateClassroom(@PathVariable Long classroomId,
                                                               @RequestBody ClassroomDto classroomDto) {
        var updatedClassroom = classroomService.update(classroomId, toClassroom(classroomDto));
        if (updatedClassroom == null) {
            logger.info("The class in this building is already created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tokia klasė šiame pastate jau sukurta."));
        }
        logger.info("The class was updated, successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (toClassroomDto(updatedClassroom).toString())));
    }

    @PatchMapping("/disable-classroom/{classroomId}")
    public ClassroomDto disableClassroom(@PathVariable Long classroomId) {
        var disableClassroom = classroomService.disable(classroomId);
        logger.log(Level.INFO, "The class was disable: {0} ", classroomId);
        return toClassroomDto(disableClassroom);
    }

    @PatchMapping("/enable-classroom/{classroomId}")
    public ClassroomDto enableClassroom(@PathVariable Long classroomId) {
        var disableClassroom = classroomService.enable(classroomId);
        logger.log(Level.INFO, "The class was enabled: {0} ", classroomId);
        return toClassroomDto(disableClassroom);
    }
}
