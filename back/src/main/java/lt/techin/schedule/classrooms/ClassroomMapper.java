package lt.techin.schedule.classrooms;

public class ClassroomMapper {
    public static ClassroomDto toClassroomDto(Classroom classroom){
        var classroomDto = new ClassroomDto();
        classroomDto.setId(classroom.getId());
        classroomDto.setClassroomName(classroom.getClassroomName());
        classroomDto.setDescription(classroom.getDescription());
        classroomDto.setActive(classroom.isActive());
        return classroomDto;
    }

    public static Classroom toClassroom(ClassroomDto classroomDto){
        var classroom = new Classroom();
        classroom.setId(classroomDto.getId());
        classroom.setClassroomName(classroomDto.getClassroomName());
        classroom.setDescription(classroomDto.getDescription());
        classroom.setActive(classroomDto.isActive());
        return classroom;
    }
}
