package lt.techin.schedule.classrooms;

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

    //    pridejau path /{id},tam kad atitiktu adresa, ieskant /classroom
    @GetMapping(value = "/classroom/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    // nereikia, @RestController jau turi sita ijunges defaultu
//    @ResponseBody
    public ResponseEntity<ClassroomDto> getClassroom(@PathVariable Long id) {
//        sito nebereikia
//        var response = new ClassroomDto();
//        pasinaudojam esamu classroommapper klase
        return ResponseEntity.ok(ClassroomMapper.toClassroomDto(classroomService.finById(id)));
    }

    //    MANO SENAS
//    @GetMapping(value = "/classrooms", produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    // nereikia, @RestController jau turi sita ijunges defaultu
//    @ResponseBody
    public List<ClassroomDto> getClassrooms() {
        return classroomService.getAll().stream().map(ClassroomMapper::toClassroomDto).toList();
    }
//      MANO PRADETAS\
//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public List<ClassroomDto> getClassrooms(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "20") int size)) {
//        return classroomService.getAll().stream().map(ClassroomMapper::toClassroomDto).collect(toList());
//    }


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
    public Classroom addBuildingToClassroom(@PathVariable Long classroomId, @RequestParam Long buildingId) {
        return classroomService.addClassroomToBuilding(classroomId, buildingId);
    }
}
