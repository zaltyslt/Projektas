package lt.techin.Schedule.shift;

import jakarta.persistence.*;
import lt.techin.Schedule.tools.TextValid;

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

    private boolean isActive;

    public Shift() {
    }

    public Shift(String name, String shiftStartingTime, String shiftEndingTime, boolean isActive) {
        this.name = name;
        this.shiftStartingTime = shiftStartingTime;
        this.shiftEndingTime = shiftEndingTime;
        this.isActive = isActive;
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
