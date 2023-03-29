package lt.techin.schedule.shifts;

import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftMapper;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.shift.ShiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ShiftServiceTests {
    @Mock
    private ShiftRepository shiftDatabase;

    @InjectMocks
    private ShiftService shiftService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUniqueShiftWithUniqueName() {
        Shift shift1 = new Shift("Shift 2", "8:00", "16:00", true, 1, 8);
        String result = shiftService.addUniqueShift(ShiftMapper.shiftToDto(shift1));
        assertEquals("", result);
    }

    @Test
    public void testAddUniqueShiftWithDuplicateName() {
        Shift shift1 = new Shift("Shift 3", "8:00", "16:00", true, 1, 8);
        Shift shift2 = new Shift("Shift 3", "9:00", "17:00", true, 2, 9);
        when(shiftDatabase.findAll()).thenReturn(Arrays.asList(shift1));
        String result = shiftService.addUniqueShift(ShiftMapper.shiftToDto(shift2));
        assertEquals("Pamainos pavadinimas turi būti unikalus.", result);
    }

    @Test
    public void testGetActiveShifts() {
        Shift shift1 = new Shift("Shift 4", "8:00", "16:00", true, 1, 8);
        Shift shift2 = new Shift("Shift 5", "9:00", "17:00", true, 2, 9);
        when(shiftDatabase.findAll()).thenReturn(Arrays.asList(shift1, shift2));
        List<Shift> activeShifts = shiftService.getActiveShifts();
        assertEquals(Arrays.asList(shift1, shift2), activeShifts);
    }

    @Test
    public void testGetInactiveShifts() {
        Shift shift1 = new Shift("Shift 6", "8:00", "16:00", false, 1, 8);
        Shift shift2 = new Shift("Shift 7", "9:00", "17:00", false, 2, 9);
        when(shiftDatabase.findAll()).thenReturn(Arrays.asList(shift1, shift2));
        List<Shift> inactiveShifts = shiftService.getInactiveShifts();
        assertEquals(Arrays.asList(shift1, shift2), inactiveShifts);
    }

    @Test
    public void testGetShiftByID() {
        Shift shift1 = new Shift("Shift 8", "8:00", "16:00", true, 1, 8);
        shift1.setId(100L);
        when(shiftDatabase.findById(100L)).thenReturn(Optional.of(shift1));
        Shift foundShift = shiftService.getShiftByID(100L);
        assertEquals(shift1, foundShift);
    }

    @Test
    public void testModifyShiftWithUniqueName() {
        Shift shift1 = new Shift("Shift 3", "8:00", "16:00", true, 1, 8);
        Shift shift2 = new Shift("Shift 4", "9:00", "17:00", true, 2, 9);
        Shift shift3 = new Shift("Shift 4", "10:00", "17:00", true, 2, 9);
        shift1.setId(200L);
        shift2.setId(100L);
        shift3.setId(100L);
        when(shiftDatabase.findById(shift2.getId())).thenReturn(Optional.of(shift2));
        when(shiftDatabase.findAll()).thenReturn(Arrays.asList(shift1, shift2));
        String result = shiftService.modifyExistingShift(shift3.getId(), ShiftMapper.shiftToDto(shift3));
        assertEquals("", result);
    }

    @Test
    public void testModifyShiftWithDuplicateName() {
        Shift shift1 = new Shift("Shift 3", "8:00", "16:00", true, 1, 8);
        Shift shift2 = new Shift("Shift 3", "9:00", "17:00", true, 2, 9);
        Shift shift3 = new Shift("Shift 3", "10:00", "17:00", true, 2, 9);
        shift1.setId(200L);
        shift2.setId(100L);
        shift3.setId(100L);
        when(shiftDatabase.findById(shift2.getId())).thenReturn(Optional.of(shift2));
        when(shiftDatabase.findAll()).thenReturn(Arrays.asList(shift1, shift2));
        String result = shiftService.modifyExistingShift(shift3.getId(), ShiftMapper.shiftToDto(shift3));
        assertEquals("Pamainos pavadinimas turi būti unikalus.", result);
    }
}

