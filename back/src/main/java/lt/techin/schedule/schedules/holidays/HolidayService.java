package lt.techin.schedule.schedules.holidays;

import jakarta.transaction.Transactional;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final ScheduleRepository scheduleRepository;

    public HolidayService(HolidayRepository holidayRepository, ScheduleRepository scheduleRepository) {
        this.holidayRepository = holidayRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public String create(HolidayPlanDto holidayPlan, Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            return "Toks tvarkaraštis neegzistuoja.";
        } else {
            //Checks if holidays being created are in range of the specified schedule
            boolean isBeforeOrEqual = (existingSchedule.getDateFrom().isEqual(holidayPlan.getDateFrom()) ||
                    (existingSchedule.getDateFrom().isBefore(holidayPlan.getDateFrom()) && existingSchedule.getDateUntil().isAfter(holidayPlan.getDateFrom())));
            boolean isAfterOrEqual = (existingSchedule.getDateUntil().isEqual(holidayPlan.getDateUntil()) ||
                    (existingSchedule.getDateUntil().isAfter(holidayPlan.getDateUntil()) && existingSchedule.getDateFrom().isBefore(holidayPlan.getDateUntil())));

            //Both dates or one of the dates is valid
            if (isBeforeOrEqual || isAfterOrEqual) {
                //One of the dates is not valid
                if (!(isBeforeOrEqual && isAfterOrEqual)) {
                    //Need to check a rare case where the holidays start at the start or end of the schedule and have 1 day duration
                    if (!holidayPlan.getDateFrom().equals(holidayPlan.getDateUntil())) {
                        //If starting date is valid and ending date exceeds schedule date
                        if (isBeforeOrEqual) {
                            holidayPlan.setDateUntil(existingSchedule.getDateUntil());
                        }
                        //If ending date is valid and starting date falls behind schedule date
                        if (isAfterOrEqual) {
                            holidayPlan.setDateFrom(existingSchedule.getDateFrom());
                        }
                    }
                }
                try {
                    //Returns true if holiday is already in schedule with in that specific date range
                    if (SetupHolidaysSetForSchedule.lookForHolidayDoublesByRange(holidayPlan.getDateFrom(), holidayPlan.getDateUntil(), existingSchedule.getHolidays())) {
                        return "Tvarkaraštyje esančios atostogos sutampa su planuojamomis atostogomis.";
                    }
                    Set<Holiday> createdHolidays = SetupHolidaysSetForSchedule.addHolidaysByPlan(holidayPlan, existingSchedule);
                    if (createdHolidays.isEmpty())
                        return "Sukurta 0 atostogų dienų. Priežastį galite sužinoti debugindami. Sėkmės!";
                    holidayRepository.saveAll(createdHolidays);

                    existingSchedule.addHolidays(createdHolidays);
                    scheduleRepository.save(existingSchedule);

                    return "";
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
            //Both dates are not valid
            else {
                return "Atostogų datos nesutampa su tvarkaraščio datomis. Tvarkaraštis prasideda/pasibaigia: " +
                        existingSchedule.getDateFrom() + "/" + existingSchedule.getDateUntil() + ". Atostogos prasideda/pasibaigia: " +
                        holidayPlan.getDateFrom() + "/" + holidayPlan.getDateUntil() + ".";
            }
            return "Atostogų sukūrimas nepavyko.";
        }
    }

    public Holiday getHolidayById(Long holidayId) {
        return holidayRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytų atostogų data neegzistuoja.", "Holiday", "Does not exist", holidayId.toString()));
    }

    public Holiday update(Long holidayId, Holiday holiday) {
        Holiday existingHoliday = holidayRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytų atostogų data neegzistuoja.", "Holiday", "Does not exist", holidayId.toString()));
        existingHoliday.setHolidayName(holiday.getHolidayName());
        existingHoliday.setDate(holiday.getDate());

        return holidayRepository.save(existingHoliday);
    }

    @Transactional
    public Boolean deleteHoliday(Long holidayId) {
        try {
            holidayRepository.deleteById(holidayId);
            return true;
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return false;
        }
    }
}
