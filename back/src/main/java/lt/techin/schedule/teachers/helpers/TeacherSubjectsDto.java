package lt.techin.schedule.teachers.helpers;

public class TeacherSubjectsDto {
    private Long subjectId;

    public TeacherSubjectsDto() {
    }
    public TeacherSubjectsDto(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "TeacherSubjectsDto{" +
                "subjectId=" + subjectId +
                '}';
    }
}
