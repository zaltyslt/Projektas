package lt.techin.schedule.subject;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.module.Module;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SubjectDto {

    private String name;

    private String description;

    private Module module;

    private Set<Classroom> classRooms;

    public SubjectDto() {
        classRooms = new HashSet<>();
    }

    public SubjectDto(String name, String description, Module module, Set<Classroom> classRooms) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.classRooms = classRooms;
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

    public Set<Classroom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(Set<Classroom> classRooms) {
        this.classRooms = classRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDto that = (SubjectDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(module, that.module) && Objects.equals(classRooms, that.classRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, module, classRooms);
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", module=" + module +
                ", classRooms=" + classRooms +
                '}';
    }
}
