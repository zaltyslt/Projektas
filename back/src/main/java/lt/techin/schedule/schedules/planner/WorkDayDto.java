package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.classrooms.ClassroomSmallDto;
import lt.techin.schedule.subject.SubjectSmallDto;
import lt.techin.schedule.teachers.TeacherEntityDto;

import java.time.LocalDate;
import java.util.Objects;

public class WorkDayDto {

    private Long id;

    private LocalDate date;

    private float lessonStart;

    private float lessonEnd;

    private Boolean online;

    private TeacherEntityDto teacher;

    private SubjectSmallDto subject;

    private ClassroomSmallDto classroom;

    public WorkDayDto() {
    }

    public WorkDayDto(Long id, LocalDate date, float lessonStart, float lessonEnd, Boolean online, TeacherEntityDto teacher, SubjectSmallDto subject, ClassroomSmallDto classroom) {
        this.id = id;
        this.date = date;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
        this.online = online;
        this.teacher = teacher;
        this.subject = subject;
        this.classroom = classroom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(float lessonStart) {
        this.lessonStart = lessonStart;
    }

    public float getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(float lessonEnd) {
        this.lessonEnd = lessonEnd;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public TeacherEntityDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntityDto teacher) {
        this.teacher = teacher;
    }

    public SubjectSmallDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectSmallDto subject) {
        this.subject = subject;
    }

    public ClassroomSmallDto getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomSmallDto classroom) {
        this.classroom = classroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDayDto that = (WorkDayDto) o;
        return Float.compare(that.lessonStart, lessonStart) == 0 && Float.compare(that.lessonEnd, lessonEnd) == 0 && Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(online, that.online) && Objects.equals(teacher, that.teacher) && Objects.equals(subject, that.subject) && Objects.equals(classroom, that.classroom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, lessonStart, lessonEnd, online, teacher, subject, classroom);
    }

    @Override
    public String toString() {
        return "WorkDayDto{" +
                "id=" + id +
                ", date=" + date +
                ", lessonStart=" + lessonStart +
                ", lessonEnd=" + lessonEnd +
                ", online=" + online +
                ", teacher=" + teacher +
                ", subject=" + subject +
                ", classroom=" + classroom +
                '}';
    }
}
