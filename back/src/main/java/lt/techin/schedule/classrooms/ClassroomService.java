package lt.techin.schedule.classrooms;

import lt.techin.schedule.classrooms.buildings.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
//    private final BuildingRepository buildingRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAll() {
        return classroomRepository.findAll();
    }

    public Classroom create(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public Classroom update(Long id, Classroom classroom) {
        var existingClassroom = classroomRepository.findById(id).orElse(null);
        if (existingClassroom != null) {
            existingClassroom.setClassroomName(classroom.getClassroomName());
            existingClassroom.setDescription(classroom.getDescription());
            existingClassroom.setBuilding(classroom.getBuilding());
            existingClassroom.setActive(classroom.isActive());
            return classroomRepository.save(existingClassroom);
        }
        return null;
    }

    public Classroom finById(Long id ) {
        return classroomRepository.findById(id).orElse(new Classroom());
    }

//    public Classroom addClassroomToBuilding(Long classroomId, Long buildingId) {
//        var existingClassroom = classroomRepository.findById(classroomId)
//                .orElse(null);
//        var existingBuilding = buildingRepository.findById(buildingId)
//                .orElse(null);
//        if (existingClassroom != null) {
//            existingClassroom.setBuilding(existingBuilding);
//            return classroomRepository.save(existingClassroom);
//        }
////        System.out.println(existingClassroom + " classroom");
////        System.out.println(existingBuilding + "  building");
//        return null;
//    }

//    public Page<Classroom> getAll(Pageable pagination) {
//        return classroomRepository.findAll(pagination);
//    }
}


