package lt.techin.schedule.subject;

public class SubjectMapper {

    public static SubjectDto toSubjectDto(Subject subject) {
        var subjectDto = new SubjectDto();

        subjectDto.setName(subject.getName());
        subjectDto.setDescription(subject.getDescription());
        subjectDto.setModule(subject.getModule());
        subjectDto.setClassRooms(subject.getClassRooms());
        subjectDto.setCreatedDate(subject.getCreatedDate());
        subjectDto.setModifiedDate(subject.getModifiedDate());

        return subjectDto;
    }

    public static SubjectEntityDto toSubjectEntityDto(Subject subject) {
        var subjectEntityDto = new SubjectEntityDto();

        subjectEntityDto.setId(subject.getId());
        subjectEntityDto.setName(subject.getName());
        subjectEntityDto.setDescription(subject.getDescription());
        subjectEntityDto.setModule(subject.getModule());
        subjectEntityDto.setClassRooms(subject.getClassRooms());
        subjectEntityDto.setCreatedDate(subject.getCreatedDate());
        subjectEntityDto.setModifiedDate(subject.getModifiedDate());

        return subjectEntityDto;
    }

    public static Subject toSubject(SubjectDto subjectDto) {
        var subject = new Subject();

        subject.setName(subjectDto.getName());
        subject.setDescription(subjectDto.getDescription());
        subject.setModule(subjectDto.getModule());
        subject.setClassRooms(subjectDto.getClassRooms());
        subject.setCreatedDate(subjectDto.getCreatedDate());
        subject.setModifiedDate(subjectDto.getModifiedDate());

        return subject;
    }

    public static Subject toSubjectFromEntityDto(SubjectEntityDto subjectEntityDto) {
        var subject = new Subject();

        subject.setId(subjectEntityDto.getId());
        subject.setName(subjectEntityDto.getName());
        subject.setDescription(subjectEntityDto.getDescription());
        subject.setModule(subjectEntityDto.getModule());
        subject.setClassRooms(subjectEntityDto.getClassRooms());
        subject.setCreatedDate(subjectEntityDto.getCreatedDate());
        subject.setModifiedDate(subjectEntityDto.getModifiedDate());

        return subject;
    }

}
