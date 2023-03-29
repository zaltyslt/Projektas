package lt.techin.schedule.teachers.helpers;

public class TeacherSubjectsDto {
    private Long subjectId;
    private String name;
    private String module;

    public TeacherSubjectsDto() {
    }

    public TeacherSubjectsDto(Long subjectId) {
        this.subjectId = subjectId;
    }

    public TeacherSubjectsDto(Long subjectId, String name, String module) {
        this.subjectId = subjectId;
        this.name = name;
        this.module = module;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeacherSubjectsDto{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}
