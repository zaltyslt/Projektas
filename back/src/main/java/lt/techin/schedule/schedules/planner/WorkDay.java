package lt.techin.schedule.schedules.planner;

import jakarta.persistence.*;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.teachers.Teacher;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String lessonStart;

    private String lessonEnd;

    private int lessonStartIntEnum;

    private int lessonEndIntEnum;

    private Boolean online;

    @ElementCollection
    @CollectionTable(name = "schedule_classroom_conflicts", joinColumns = @JoinColumn(name = "classroom_id"))
    @MapKeyColumn(name = "schedule_id")
    @Column(name = "classroom_name")
    private Map<Long, String> scheduleIdWithClassroomNameConflict;

    @ElementCollection
    @CollectionTable(name = "schedule_teacher_conflicts", joinColumns = @JoinColumn(name = "teacher_id"))
    @MapKeyColumn(name = "schedule_id")
    @Column(name = "teacher_name")
    private Map<Long, String> scheduleIdWithTeacherNameConflict;

    private boolean hasTeacherConflict;

    private boolean hasClassroomConflict;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public WorkDay() {
        scheduleIdWithTeacherNameConflict = new HashMap<>();
        scheduleIdWithClassroomNameConflict = new HashMap<>();
        hasTeacherConflict = false;
        hasClassroomConflict = false;
    }

    public WorkDay(LocalDate date, Subject subject, Teacher teacher, Schedule schedule,
                   Classroom classroom, String lessonStart, String lessonEnd, int lessonStartIntEnum,
                   int lessonEndIntEnum, Boolean online) {
        this.date = date;
        this.subject = subject;
        this.teacher = teacher;
        this.schedule = schedule;
        this.classroom = classroom;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
        this.lessonStartIntEnum = lessonStartIntEnum;
        this.lessonEndIntEnum = lessonEndIntEnum;
        this.online = online;
        scheduleIdWithTeacherNameConflict = new HashMap<>();
        scheduleIdWithClassroomNameConflict = new HashMap<>();
    }

    public WorkDay(Long id) {
        this.id = id;
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

    public String getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(String lessonStart) {
        this.lessonStart = lessonStart;
    }

    public String getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(String lessonEnd) {
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

    public int getLessonStartIntEnum() {
        return lessonStartIntEnum;
    }

    public void setLessonStartIntEnum(int lessonStartIntEnum) {
        this.lessonStartIntEnum = lessonStartIntEnum;
    }

    public int getLessonEndIntEnum() {
        return lessonEndIntEnum;
    }

    public void setLessonEndIntEnum(int lessonEndIntEnum) {
        this.lessonEndIntEnum = lessonEndIntEnum;
    }

    public void setScheduleIdWithTeacherNameConflict(Map<Long, String> scheduleIdWithTeacherNameConflict) {
        this.scheduleIdWithTeacherNameConflict = scheduleIdWithTeacherNameConflict;
    }

    public void setScheduleIdWithClassroomNameConflict(Map<Long, String> scheduleIdWithClassroomNameConflict) {
        this.scheduleIdWithClassroomNameConflict = scheduleIdWithClassroomNameConflict;
    }

    public Map<Long, String> getScheduleIdWithTeacherNameConflict() {
        return scheduleIdWithTeacherNameConflict;
    }

    public Map<Long, String> getScheduleIdWithClassroomNameConflict() {
        return scheduleIdWithClassroomNameConflict;
    }

    public boolean isHasTeacherConflict() {
        return hasTeacherConflict;
    }

    public void setHasTeacherConflict(boolean hasTeacherConflict) {
        this.hasTeacherConflict = hasTeacherConflict;
    }

    public boolean isHasClassroomConflict() {
        return hasClassroomConflict;
    }

    public void setHasClassroomConflict(boolean hasClassroomConflict) {
        this.hasClassroomConflict = hasClassroomConflict;
    }

    public void addClassroomConflict(Long scheduleID, String classroomName) {
        scheduleIdWithClassroomNameConflict.put(scheduleID, classroomName);
    }

    public void addTeacherConflict(Long scheduleID, String teacherName) {
        scheduleIdWithTeacherNameConflict.put(scheduleID, teacherName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkDay workDay)) return false;
        return Objects.equals(id, workDay.id) && Objects.equals(date, workDay.date) && Objects.equals(subject, workDay.subject) && Objects.equals(teacher, workDay.teacher) && Objects.equals(schedule, workDay.schedule) && Objects.equals(classroom, workDay.classroom) && Objects.equals(lessonStart, workDay.lessonStart) && Objects.equals(lessonEnd, workDay.lessonEnd) && Objects.equals(online, workDay.online);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, subject, teacher, schedule, classroom, lessonStart, lessonEnd, online);
    }
}
