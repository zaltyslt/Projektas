package lt.techin.schedule.schedules.holidays;

import java.time.LocalDate;
import java.util.Objects;

public class LithuanianHolidayDto {
    private String name;
    private LocalDate date;

    public LithuanianHolidayDto(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LithuanianHolidayDto that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }

    @Override
    public String toString() {
        return "LithuanianHolidayDto{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
