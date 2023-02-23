package lt.techin.schedule.module;

import lt.techin.schedule.config.DataFieldsLengthConstraints;
import lt.techin.schedule.validators.TextValid;

import java.util.Objects;

public class ModuleDto {

    @TextValid(textMaximumLength = DataFieldsLengthConstraints.moduleNumberMaximumLength)
    private String number;
    @TextValid(textMaximumLength = DataFieldsLengthConstraints.moduleNameMaximumLength)
    private String name;

    public ModuleDto() {
    }

    public ModuleDto(String number, String name) {
        this.number = number;
        this.name = name;
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
        return Objects.equals(number, moduleDto.number) && Objects.equals(name, moduleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name);
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

