package lt.techin.schedule.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.config.DataFieldsLengthConstraints;
import lt.techin.schedule.programs.ProgramDto;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftDto;
import lt.techin.schedule.validators.TextValid;

import java.time.LocalDateTime;
import java.util.Objects;

public class GroupDto {

    private Long id;

    @TextValid(textMaximumLength = DataFieldsLengthConstraints.TEXT_FIELD_MAXIMUM_LENGTH)
    private String name;

    private int year;

    private int studentAmount;

    private boolean isActive;

    private ProgramDto program;
    private ShiftDto shift;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;


    public GroupDto() {}

    public GroupDto(Long id, String name, int year, int studentAmount, boolean isActive, ProgramDto program, ShiftDto shift, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.studentAmount = studentAmount;
        this.isActive = isActive;
        this.program = program;
        this.shift = shift;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getStudentAmount() {
        return studentAmount;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public ShiftDto getShift() {
        return shift;
    }

    public ProgramDto getProgram() {
        return program;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void setShift(ShiftDto shift) {
        this.shift = shift;
    }

    public void setProgram(ProgramDto program) {
        this.program = program;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDto groupDto)) return false;
        return year == groupDto.year && studentAmount == groupDto.studentAmount && id.equals(groupDto.id) && name.equals(groupDto.name) && program.equals(groupDto.program) && shift.equals(groupDto.shift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, studentAmount, program, shift);
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", studentAmount=" + studentAmount +
                ", program=" + program +
                ", shift=" + shift +
                '}';
    }
}
