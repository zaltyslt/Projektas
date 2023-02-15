package lt.techin.schedule.subject;

public class SubjectMapper {

    public static SubjectDto toSubjectDto(Subject subject) {
        var subjectDto = new SubjectDto();

        subjectDto.setName(subject.getName());
        subjectDto.setDescription(subject.getDescription());
        subjectDto.setModule(subject.getModule());
        subjectDto.setClassRooms(subject.getClassRooms());

        return subjectDto;
    }

    public static SubjectEntityDto toSubjectEntityDto(Subject subject) {
        var subjectEntityDto = new SubjectEntityDto();

        subjectEntityDto.setId(subject.getId());
        subjectEntityDto.setName(subject.getName());
        subjectEntityDto.setDescription(subject.getDescription());
        subjectEntityDto.setModule(subject.getModule());
        subjectEntityDto.setClassRooms(subject.getClassRooms());

        return subjectEntityDto;
    }

    public static Subject toSubject(SubjectDto subjectDto) {
        var subject = new Subject();

        subject.setName(subjectDto.getName());
        subject.setDescription(subjectDto.getDescription());
        subject.setModule(subjectDto.getModule());
        subject.setClassRooms(subjectDto.getClassRooms());

        return subject;
    }

    public static Subject toSubjectFromEntityDto(SubjectEntityDto subjectEntityDto) {
        var subject = new Subject();

        subject.setId(subjectEntityDto.getId());
        subject.setName(subjectEntityDto.getName());
        subject.setDescription(subjectEntityDto.getDescription());
        subject.setModule(subjectEntityDto.getModule());
        subject.setClassRooms(subjectEntityDto.getClassRooms());

        return subject;
    }

}
