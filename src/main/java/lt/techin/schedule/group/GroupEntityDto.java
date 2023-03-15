package lt.techin.schedule.group;

import java.util.Objects;

public class GroupEntityDto {

    private Long id;

    private String name;

    public GroupEntityDto() {
    }

    public GroupEntityDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroupEntityDto that = (GroupEntityDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        return "GroupEntityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
