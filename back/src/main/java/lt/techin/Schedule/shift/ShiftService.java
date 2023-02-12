package lt.techin.schedule.shift;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    private ShiftDTO shiftDatabase;

    public ShiftService(ShiftDTO shiftDatabase) {
        this.shiftDatabase = shiftDatabase;
    }

    public void addShift(Shift shift) {
        shiftDatabase.save(shift);
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
}
