package lt.techin.Schedule.shift;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ShiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private LessonTime shiftStartingLesson;
    private LessonTime shiftEndingLesson;

    public ShiftEntity() {}

    public ShiftEntity(String name, LessonTime shiftStartingLesson, LessonTime shiftEndingLesson) {
        this.name = name;
        this.shiftStartingLesson = shiftStartingLesson;
        this.shiftEndingLesson = shiftEndingLesson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LessonTime getShiftStartingLesson() {
        return shiftStartingLesson;
    }

    public void setShiftStartingLesson(LessonTime shiftStartingLesson) {
        this.shiftStartingLesson = shiftStartingLesson;
    }

    public LessonTime getShiftEndingLesson() {
        return shiftEndingLesson;
    }

    public void setShiftEndingLesson(LessonTime shiftEndingLesson) {
        this.shiftEndingLesson = shiftEndingLesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftEntity that)) return false;
        return id.equals(that.id) && Objects.equals(name, that.name) && shiftStartingLesson == that.shiftStartingLesson && shiftEndingLesson == that.shiftEndingLesson;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shiftStartingLesson, shiftEndingLesson);
    }

    @Override
    public String toString() {
        return "ShiftEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shiftStartingLesson=" + shiftStartingLesson +
                ", shiftEndingLesson=" + shiftEndingLesson +
                '}';
    }
}
