package lt.techin.schedule.subject;

import lt.techin.schedule.module.Module;
import lt.techin.schedule.room.Room;

import java.util.Objects;
import java.util.Set;

public class SubjectDto {

    private String name;

    private String description;

    private Module module;

//    private Set<Room> rooms;

    public SubjectDto() {
    }

    public SubjectDto(String name, String description, Module module) {
        this.name = name;
        this.description = description;
        this.module = module;
//        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

//    public Set<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(Set<Room> rooms) {
//        this.rooms = rooms;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDto that = (SubjectDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(module, that.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, module);
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", module=" + module +
//                ", rooms=" + rooms +
                '}';
    }
}
