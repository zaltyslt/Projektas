package lt.techin.schedule.programs.subjectsHours;

public class SubjectHoursMapper {
    public static SubjectHoursDto toSubjectHoursDto(SubjectHours subjectHours){
        var subjectHoursDto = new SubjectHoursDto();
        subjectHoursDto.setId(subjectHours.getId());
        subjectHoursDto.setSubjectName(subjectHours.getSubjectName());
        subjectHoursDto.setSubject(subjectHours.getSubject());
        subjectHoursDto.setHours(subjectHours.getHours());
        subjectHoursDto.setCreatedDate(subjectHours.getCreatedDate());
        subjectHoursDto.setModifiedDate(subjectHours.getModifiedDate());

        return subjectHoursDto;
    }

    public static SubjectHours toSubjectHours(SubjectHoursDto subjectHoursDto){
        var subjectHours = new SubjectHours();
        subjectHours.setId(subjectHoursDto.getId());
        subjectHours.setSubjectName(subjectHoursDto.getSubjectName());
        subjectHours.setSubject(subjectHoursDto.getSubject());
        subjectHours.setHours(subjectHoursDto.getHours());
        subjectHours.setCreatedDate(subjectHoursDto.getCreatedDate());
        subjectHours.setModifiedDate(subjectHoursDto.getModifiedDate());

        return subjectHours;
    }
}
