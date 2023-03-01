package lt.techin.schedule.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.programs.ProgramDto;
import lt.techin.schedule.shift.Shift;

import java.time.LocalDateTime;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private int year;

    private int studentAmount;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;


    public Group() { }

    public Group(Long id, String name, int year, int studentAmount, Program program, Shift shift, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.studentAmount = studentAmount;
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

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getStudentAmount() {
        return studentAmount;
    }

    public Long getId() {
        return id;
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


    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setProgram(Program program) {
        this.program = program;
    }


    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
