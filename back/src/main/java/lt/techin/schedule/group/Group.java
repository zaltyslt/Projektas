package lt.techin.schedule.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.programs.ProgramDto;
import lt.techin.schedule.shift.Shift;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String year;

    private int studentAmount;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;


    public Group() { }

    public Group(Long id, String name, String year, int studentAmount, boolean isActive, Program program, Shift shift, LocalDateTime createdDate, LocalDateTime modifiedDate) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public int getStudentAmount() {
        return studentAmount;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Program getProgram() {
        return program;
    }

    public Shift getShift() {
        return shift;
    }


    @PrePersist
    public void prePersist() {
        modifiedDate = LocalDateTime.now();
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return year == group.year && studentAmount == group.studentAmount && id.equals(group.id) && name.equals(group.name) && isActive.equals(group.isActive) && Objects.equals(program, group.program) && Objects.equals(shift, group.shift) && Objects.equals(createdDate, group.createdDate) && Objects.equals(modifiedDate, group.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, studentAmount, isActive, program, shift, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", studentAmount=" + studentAmount +
                ", isActive=" + isActive +
                ", program=" + program +
                ", shift=" + shift +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
