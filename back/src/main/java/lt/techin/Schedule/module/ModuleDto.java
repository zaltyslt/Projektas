package lt.techin.Schedule.module;

import java.util.Objects;

public class ModuleDto {

    private Long id;
    private String number;
    private String name;

    public ModuleDto() {
    }

    public ModuleDto(Long id, String number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        ModuleDto moduleDto = (ModuleDto) o;
        return Objects.equals(id, moduleDto.id) && Objects.equals(number, moduleDto.number) && Objects.equals(name, moduleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name);
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
