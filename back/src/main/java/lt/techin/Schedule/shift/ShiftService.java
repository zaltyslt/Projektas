package lt.techin.Schedule.shift;


import org.springframework.stereotype.Service;

@Service
public class ShiftService {

    private ShiftDTO shiftDatabase;

    public ShiftService(ShiftDTO shiftDatabase) {
        this.shiftDatabase = shiftDatabase;
    }

    public void addShift(Shift shift) {
        shiftDatabase.save(shift);
    }

    public void getShifts() {

    }

}
