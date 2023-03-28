package lt.techin.schedule.schedules.holidays;

import jakarta.transaction.Transactional;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import org.springframework.dao.OptimisticLockingFailureException;
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
        var aa = holidayRepository.findByScheduleId(id);
        return aa;
    }

    public String create(Holiday holiday, Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            return "Toks tvarkaraštis neegzistuoja.";
        } else {
            //Checks if holidays being created are in range of specified schedule
            if ((existingSchedule.getDateFrom().isEqual(holiday.getDateFrom()) || existingSchedule.getDateFrom().isBefore(holiday.getDateFrom())) &&
                    (existingSchedule.getDateUntil().isEqual(holiday.getDateUntil()) || existingSchedule.getDateUntil().isAfter(holiday.getDateUntil())))
            {
                try {
                    Holiday holiday1 = new Holiday();
                    holiday1.setHolidayName(holiday.getHolidayName());
                    holiday1.setDateFrom(holiday.getDateFrom());
                    holiday1.setDateUntil(holiday.getDateUntil());
                    holiday1.setSchedule(existingSchedule);
                    Holiday save = holidayRepository.save(holiday1);
                    existingSchedule.addHoliday(holiday1);
                    return "";
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
            else {
                return "Atostogų datos nesutampa su tvarkaraščio datomis. Tvarkaraštis prasideda/pasibaigia: " +
                        existingSchedule.getDateFrom() + "/" + existingSchedule.getDateUntil() + ". Atostogos prasideda/pasibaigia: " +
                        holiday.getDateFrom() + "/" + holiday.getDateUntil() + ".";
            }
            return "Atostogų sukūrimas nepavyko.";
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

    @Transactional
    public Boolean deleteHoliday(Long holidayId) {
        Holiday existingHoliday = holidayRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytos atostogos neegzistuoja", "Holiday", "Does not exist", holidayId.toString()));
        try {
            holidayRepository.deleteById(holidayId);
            return true;
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return false;
        }
    }
}
