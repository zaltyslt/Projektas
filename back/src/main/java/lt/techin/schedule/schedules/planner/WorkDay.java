package lt.techin.schedule.schedules.planner;

import jakarta.persistence.*;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.teachers.Teacher;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public LocalDate date;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    public Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_teacher_id")
    public Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private float lessonStart;

    private float lessonEnd;

    private Boolean online;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public WorkDay() {
    }

    public WorkDay(LocalDate date, Subject subject, Teacher teacher, Schedule schedule, Classroom classroom, float lessonStart, float lessonEnd, Boolean online) {
        this.date = date;
        this.subject = subject;
        this.teacher = teacher;
        this.schedule = schedule;
        this.classroom = classroom;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
        this.online = online;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkDay workDay)) return false;
        return date.equals(workDay.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
