package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.schedules.planner.WorkDay;

import java.time.LocalDate;
import java.util.List;

public class Month {
    private int index;
    private String group;
    private List<WorkDay> workDays;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LocalDate getFirstDay() {
        if (!workDays.isEmpty()) {
            return workDays.get(0).getDate();

        }
        return null;
    }

    public LocalDate getLastDay() {
        if (!workDays.isEmpty()) {
            return workDays.get(workDays.size() - 1).getDate();
        }
return null;
    }

    public String getGroup() {
        if (!workDays.isEmpty()) {
            this.group = workDays.stream()
                    .filter(obj -> obj.getSchedule() != null)
                    .findFirst()
                    .map(obj -> obj.getSchedule().getGroups().getName())
                    .orElse(null);
        }
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<WorkDay> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<WorkDay> workDays) {
        this.workDays = workDays;
    }

    @Override
    public String toString() {
        return "Month{" +
                "index=" + index +
                ", date=" + getFirstDay() + " - " + getLastDay() +
                ", group='" + group + '\'' +
                ", workDays=" + workDays +
                '}';
    }
}
