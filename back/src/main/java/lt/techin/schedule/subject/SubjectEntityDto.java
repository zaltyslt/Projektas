package lt.techin.schedule.subject;

import lt.techin.schedule.module.Module;
import lt.techin.schedule.room.Room;

import java.util.Objects;
import java.util.Set;

public class SubjectEntityDto extends SubjectDto {

    private Long id;

    public SubjectEntityDto() {
    }

    public SubjectEntityDto(String name, String description, Module module, Set<Room> rooms, Long id) {
        super(name, description, module, rooms);
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubjectEntityDto that = (SubjectEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "SubjectEntityDto{" +
                "id=" + id +
                '}';
    }
}
