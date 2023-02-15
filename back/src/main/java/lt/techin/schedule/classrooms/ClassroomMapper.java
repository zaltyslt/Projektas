package lt.techin.schedule.classrooms;

public class ClassroomMapper {
    public static ClassroomDto toClassroomDto(Classroom classroom){
        var classroomDto = new ClassroomDto();
        classroomDto.setId(classroom.getId());
        classroomDto.setClassroomName(classroom.getClassroomName());
        classroomDto.setDescription(classroom.getDescription());
        classroomDto.setBuilding(classroom.getBuilding());
        classroomDto.setActive(classroom.isActive());
        classroomDto.setCreatedDate(classroom.getCreatedDate());
        classroomDto.setModifiedDate(classroom.getModifiedDate());
        classroomDto.setSubjects(classroom.getSubjects());
        return classroomDto;
    }

    public static Classroom toClassroom(ClassroomDto classroomDto){
        var classroom = new Classroom();
        classroom.setId(classroomDto.getId());
        classroom.setClassroomName(classroomDto.getClassroomName());
        classroom.setDescription(classroomDto.getDescription());
        classroom.setBuilding(classroomDto.getBuilding());
        classroom.setActive(classroomDto.isActive());
        classroom.setCreatedDate(classroomDto.getCreatedDate());
        classroom.setModifiedDate(classroomDto.getModifiedDate());
        classroom.setSubjects(classroomDto.getSubjects());
        return classroom;
    }
}
