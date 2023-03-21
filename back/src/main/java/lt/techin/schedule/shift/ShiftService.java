package lt.techin.schedule.shift;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    private final ShiftRepository shiftDatabase;

    public ShiftService(ShiftRepository shiftDatabase) {
        this.shiftDatabase = shiftDatabase;
    }

    public void addShift(Shift shift) {
        shiftDatabase.save(shift);
    }

    private Optional<Shift> findShiftByName(String name) {
        return shiftDatabase.findAll().stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
    }

    private static ShiftDto setShiftDtoStringFields (ShiftDto shiftDto) {
        //These values are null, because client sets int enums only.
        if (shiftDto.getShiftStartingTime() == null || shiftDto.getShiftStartingTime().isEmpty()) {
            shiftDto.setShiftStartingTime(LessonTime.getLessonTimeByInt(shiftDto.getStartIntEnum()).getLessonStart());
        }
        if (shiftDto.getShiftEndingTime() == null || shiftDto.getShiftEndingTime().isEmpty()) {
            shiftDto.setShiftEndingTime(LessonTime.getLessonTimeByInt(shiftDto.getEndIntEnum()).getLessonEnd());
        }
        return shiftDto;
    }

    public String addUniqueShift(ShiftDto shiftDto) {
        if(findShiftByName(shiftDto.getName()).isPresent()) {
            return "Pamainos pavadinimas turi būti unikalus.";
        }
        else {
            Shift shiftToSave = ShiftMapper.dtoToShift(setShiftDtoStringFields(shiftDto));
            shiftDatabase.save(shiftToSave);
            return "";
        }
    }

    private final Comparator<Shift> compareShiftByName = Comparator.comparing(o -> o.getName().toLowerCase());

    public List<Shift> getActiveShifts() {
        return shiftDatabase.findAll().stream().filter(Shift::getIsActive).sorted(compareShiftByName).collect(Collectors.toList());
    }

    public List<Shift> getInactiveShifts() {
        return shiftDatabase.findAll().stream().filter(s -> !s.getIsActive()).sorted(compareShiftByName).collect(Collectors.toList());
    }

    public Shift getShiftByID(Long shiftID) {
        return shiftDatabase.findById(shiftID).orElse(null);
    }

    public String modifyExistingShift(Long shiftID, ShiftDto shiftDto) {
        //Checks whether a shift with the same ID is present in a database
        if (shiftDatabase.findById(shiftID).isPresent()) {
            Optional<Shift> foundShift = findShiftByName(shiftDto.getName());
            /*
            Checks whether there is a shift with a same name present in a database
            If there is, checks if this is the same shift being modified
            (You can modify other shift fields leaving the name unchanged)
            If that name is not present in a database or this is the same shift it doesn't return string here
             */
            if(foundShift.isPresent() && !foundShift.get().getId().equals(shiftID)) {
                return "Pamainos pavadinimas turi būti unikalus.";
            }
            if (shiftDto.getId() == null) {
                shiftDto.setId(shiftID);
            }
            if (shiftDto.getCreatedDate() == null) {
                shiftDto.setCreatedDate(shiftDatabase.findById(shiftID).get().getCreatedDate());
            }
            Shift shiftToSave = ShiftMapper.dtoToShift(setShiftDtoStringFields(shiftDto));
            shiftDatabase.save(shiftToSave);
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
