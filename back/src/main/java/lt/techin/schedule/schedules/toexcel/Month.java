package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.group.Group;
import lt.techin.schedule.schedules.planner.WorkDay;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;

import java.time.LocalDate;
import java.util.List;

public class Month {
    private int index;
    private LocalDate date;
    private String group;
    private List<WorkDay> workDays;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LocalDate getDate() {
       if(!workDays.isEmpty()){
         this.date = workDays.get(0).getDate();
       }

        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getGroup() {
        if(!workDays.isEmpty()){
            this.group = workDays.stream()
                    .filter(obj -> obj.getSchedule() != null)
                    .findFirst()
                    .map(obj -> obj.getSchedule().getGroups().getName())
                    .orElse(null);


//                    .getGroups().getName();
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
                ", date=" + date +
                ", group='" + group + '\'' +
                ", workDays=" + workDays +
                '}';
    }
}
