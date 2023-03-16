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
        return classroom;
    }

    public static Classroom toClassroomFromSmallDto(ClassroomSmallDto classroomSmallDto) {
        var classroom = new Classroom();

        if (classroomSmallDto != null) {
            classroom.setId(classroomSmallDto.getId());
            classroom.setClassroomName(classroomSmallDto.getClassroomName());
            return classroom;
        } else {
            return null;
        }
    }

    public static ClassroomSmallDto toClassroomSmallDto(Classroom classroom) {
        var classroomSmallDto = new ClassroomSmallDto();

        if(classroom != null) {
            classroomSmallDto.setId(classroom.getId());
            classroomSmallDto.setClassroomName(classroom.getClassroomName());
            return classroomSmallDto;
        } else {
            return null;
        }
    }
}
