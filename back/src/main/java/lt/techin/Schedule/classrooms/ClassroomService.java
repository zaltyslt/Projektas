package lt.techin.schedule.classrooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAll() {
        return classroomRepository.findAll();
    }

    public Classroom create(Classroom classroom) {
        if (classroom.getBuilding() == null) return null;
        if (findByClassroomNameAndBuilding(classroom.getClassroomName(), classroom.getBuilding())) {
            return null;
        } else {
            try {
                return classroomRepository.save(classroom);

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            return null;
        }
    }

    public Classroom update(Long id, Classroom classroom) {
        var existingClassroom = classroomRepository.findById(id).orElse(null);
        if (existingClassroom != null) {
            if (!existingClassroom.getBuilding().equals(classroom.getBuilding())
                    && (findByClassroomNameAndBuilding(classroom.getClassroomName(), classroom.getBuilding())))
                return null;
            existingClassroom.setClassroomName(classroom.getClassroomName());
            existingClassroom.setDescription(classroom.getDescription());
            existingClassroom.setBuilding(classroom.getBuilding());
            existingClassroom.setActive(classroom.isActive());
            return classroomRepository.save(existingClassroom);
        }
        return null;
    }

    public Classroom finById(Long id) {
        return classroomRepository.findById(id).orElse(new Classroom());
    }

    public Classroom disable(Long id) {

        var existingClassroom = classroomRepository.findById(id).orElse(null);
        if (existingClassroom != null) {
            existingClassroom.setActive(false);
            return classroomRepository.save(existingClassroom);
        }
        return null;
    }

    public Classroom enable(Long classroomId) {
        var existingClassroom = classroomRepository.findById(classroomId).orElse(null);
        if (existingClassroom != null) {
            existingClassroom.setActive(true);
            return classroomRepository.save(existingClassroom);
        }
        return null;
    }

    public boolean findByClassroomNameAndBuilding(String classroomName, BuildingType building) {
        return getAll().stream().anyMatch(classroom -> classroom.getClassroomName().matches(classroomName)
                && classroom.getBuilding().equals(building));
    }
}


