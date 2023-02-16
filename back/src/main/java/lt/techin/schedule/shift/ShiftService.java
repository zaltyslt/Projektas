package lt.techin.schedule.shift;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    private final ShiftDTO shiftDatabase;

    public ShiftService(ShiftDTO shiftDatabase) {
        this.shiftDatabase = shiftDatabase;
    }

    public void addShift(Shift shift) {
        shiftDatabase.save(shift);
    }

    private boolean findShiftByName(String name) {
        return shiftDatabase.findAll().stream().anyMatch(s -> s.getName().equalsIgnoreCase(name));
    }

    public String addUniqueShift(Shift shift) {
        if(findShiftByName(shift.getName())) {
            return "Pamainos pavadinimas turi būti unikalus.";
        }
        else {
            int startInteger = shift.getStartIntEnum();
            int endingInteger = shift.getEndIntEnum();
            shiftDatabase.save(new Shift(shift.getName(),
                    LessonTime.getLessonTimeByInt(startInteger).getLessonStart(),
                    LessonTime.getLessonTimeByInt(endingInteger).getLessonEnd(),
                    shift.getIsActive(),
                    startInteger,
                    endingInteger));
            return "";
        }
    }

    public List<Shift> getActiveShifts() {
        return shiftDatabase.findAll().stream().filter(Shift::getIsActive).collect(Collectors.toList());
    }

    public List<Shift> getInactiveShifts() {
        return shiftDatabase.findAll().stream().filter(s -> !s.getIsActive()).collect(Collectors.toList());
    }

    public Shift getShiftByID(Long shiftID) {
        return shiftDatabase.findAll().stream().filter(s -> Objects.equals(s.getId(), shiftID)).findAny().orElse(null);
    }

    public String modifyExistingShift(Long shiftID, Shift shift) {
        if (shiftDatabase.findById(shiftID).isPresent()) {
            if(findShiftByName(shift.getName())) {
                return "Pamainos pavadinimas turi būti unikalus.";
            }
            if (shift.getId() == null) {
                shift.setId(shiftID);
            }
            if (shift.getCreatedDate() == null) {
                shift.setCreatedDate(shiftDatabase.findById(shiftID).get().getCreatedDate());
            }
            shift.setShiftStartingTime(LessonTime.getLessonTimeByInt(shift.getStartIntEnum()).getLessonStart());
            shift.setShiftEndingTime(LessonTime.getLessonTimeByInt(shift.getEndIntEnum()).getLessonEnd());
            shiftDatabase.save(shift);
            return "";
        }
        return "Pamainos pakeisti nepavyko.";
    }

    public void changeActiveShiftStatusByID(Long shiftID, boolean activeStatus) {
        Optional<Shift> shiftToActivate = shiftDatabase.findById(shiftID);
        if (shiftToActivate.isPresent()) {
            shiftToActivate.get().setIsActive(activeStatus);
            shiftDatabase.save(shiftToActivate.get());
        }
    }
}
