package lt.techin.schedule.schedules;

import jakarta.persistence.*;
import lt.techin.schedule.shift.Shift;
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
    @JoinColumn(name = "shift_id")
    public Shift shift;

    public WorkDay() {
    }

    public WorkDay(LocalDate date, Subject subject, Teacher teacher, Shift shift) {
        this.date = date;
        this.subject = subject;
        this.teacher = teacher;
        this.shift = shift;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkDay workDay)) return false;
        return Objects.equals(id, workDay.id) && Objects.equals(date, workDay.date) && Objects.equals(subject, workDay.subject) && Objects.equals(teacher, workDay.teacher) && Objects.equals(shift, workDay.shift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, subject, teacher, shift);
    }
}
