package lt.techin.schedule.teachers.helpers;

import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.validators.TextValid;

import java.util.Objects;

/**
 * A DTO for the {@link Shift} entity.
 */

public class TeacherShiftDto {
    private Long id;
    @TextValid(textMaximumLength = 50)
    private String name;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherShiftDto that = (TeacherShiftDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TeacherShiftDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
