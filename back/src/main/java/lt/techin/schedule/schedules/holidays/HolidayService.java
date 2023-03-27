package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final ScheduleRepository scheduleRepository;

    public HolidayService(HolidayRepository holidayRepository, ScheduleRepository scheduleRepository) {
        this.holidayRepository = holidayRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }

    public List<Holiday> getById(Long id) {
        return holidayRepository.findByScheduleId(id);
    }

    public Holiday create(Holiday holiday, Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            return null;
        } else {
            try {
                Holiday holiday1 = new Holiday();
                holiday1.setHolidayName(holiday.getHolidayName());
                holiday1.setDateFrom(holiday.getDateFrom());
                holiday1.setDateUntil(holiday.getDateUntil());
                holiday1.setSchedule(existingSchedule);
                Holiday save = holidayRepository.save(holiday1);
                existingSchedule.addHoliday(holiday1);
                return save;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            return null;
        }
    }

    public Holiday getHolidayById(Long holidayId) {
        return holidayRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytos atostogos neegzistuoja.", "Holiday", "Does nor exist", holidayId.toString()));
    }

    public Holiday update(Long holidayId, Holiday holiday) {
        Holiday existingHoliday = holidayRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytos atostogos neegzistuoja.", "Holiday", "Does nor exist", holidayId.toString()));
        existingHoliday.setHolidayName(holiday.getHolidayName());
        existingHoliday.setDateFrom(holiday.getDateFrom());
        existingHoliday.setDateUntil(holiday.getDateUntil());

        return holidayRepository.save(existingHoliday);
    }
}
