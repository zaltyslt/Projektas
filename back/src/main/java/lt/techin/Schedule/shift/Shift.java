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

    private float shiftStartingTime;
    private float shiftEndingTime;

    private boolean isActive;

    public Shift() {
    }

    public Shift(String name, float shiftStartingTime, float shiftEndingTime, boolean isActive) {
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

    public float getShiftStartingTime() {
        return shiftStartingTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setShiftStartingTime(float shiftStartingTime) {
        this.shiftStartingTime = shiftStartingTime;
    }

    public float getShiftEndingTime() {
        return shiftEndingTime;
    }

    public void setShiftEndingTime(float shiftEndingTime) {
        this.shiftEndingTime = shiftEndingTime;
    }

    public void setIsActive (boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shift shift)) return false;
        return Float.compare(shift.shiftStartingTime, shiftStartingTime) == 0 && Float.compare(shift.shiftEndingTime, shiftEndingTime) == 0 && id.equals(shift.id) && Objects.equals(name, shift.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shiftStartingTime, shiftEndingTime);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shiftStartingTime=" + shiftStartingTime +
                ", shiftEndingTime=" + shiftEndingTime +
                '}';
    }
}
