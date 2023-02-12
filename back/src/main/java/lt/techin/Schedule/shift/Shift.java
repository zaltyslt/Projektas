package lt.techin.schedule.shift;

import jakarta.persistence.*;
import lt.techin.Schedule.tools.TextValid;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @TextValid(textMaximumLength = 30)
    private String name;

    @TextValid(textMaximumLength = 6)
    private String shiftStartingTime;

    @TextValid(textMaximumLength = 6)
    private String shiftEndingTime;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private int startIntEnum;
    private int endIntEnum;
    private boolean isActive;

    public Shift() {
    }

    public Shift(String name, String shiftStartingTime, String shiftEndingTime, boolean isActive, int startIntEnum, int endIntEnum) {
        this.name = name;
        this.shiftStartingTime = shiftStartingTime;
        this.shiftEndingTime = shiftEndingTime;
        this.isActive = isActive;
        this.startIntEnum = startIntEnum;
        this.endIntEnum = endIntEnum;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
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

    public void setName(String name) {
        this.name = name;
    }

    public String getShiftStartingTime() {
        return shiftStartingTime;
    }

    public void setShiftStartingTime(String shiftStartingTime) {
        this.shiftStartingTime = shiftStartingTime;
    }

    public String getShiftEndingTime() {
        return shiftEndingTime;
    }

    public void setShiftEndingTime(String shiftEndingTime) {
        this.shiftEndingTime = shiftEndingTime;
    }

    public String getShiftTime() {
        return shiftStartingTime + "-" + shiftEndingTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public int getStartIntEnum() {
        return startIntEnum;
    }

    public void setStartIntEnum(int startIntEnum) {
        this.startIntEnum = startIntEnum;
    }

    public int getEndIntEnum() {
        return endIntEnum;
    }

    public void setEndIntEnum(int endIntEnum) {
        this.endIntEnum = endIntEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shift shift)) return false;
        return isActive == shift.isActive && id.equals(shift.id) && Objects.equals(name, shift.name) && Objects.equals(shiftStartingTime, shift.shiftStartingTime) && Objects.equals(shiftEndingTime, shift.shiftEndingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shiftStartingTime, shiftEndingTime, isActive);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shiftStartingTime='" + shiftStartingTime + '\'' +
                ", shiftEndingTime='" + shiftEndingTime + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
