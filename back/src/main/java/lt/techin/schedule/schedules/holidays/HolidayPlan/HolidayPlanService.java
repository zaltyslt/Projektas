package lt.techin.schedule.schedules.holidays.HolidayPlan;

import jakarta.transaction.Transactional;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.holidays.HolidayRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayPlanService {
    private final HolidayRepository holidayRepository;

    private final HolidayPlanRepository holidayPlanRepository;
    private final ScheduleRepository scheduleRepository;

    public HolidayPlanService(HolidayRepository holidayRepository, HolidayPlanRepository holidayPlanRepository, ScheduleRepository scheduleRepository) {
        this.holidayRepository = holidayRepository;
        this.holidayPlanRepository = holidayPlanRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<HolidayPlan> getAll() {
        return holidayPlanRepository.findAll();
    }

    public List<HolidayPlan> getById(Long id) {
        return holidayPlanRepository.findByScheduleId(id);
    }

    public String create(HolidayPlan holidayPlan, Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            return "Toks tvarkaraštis neegzistuoja.";
        } else {
            //Checks if holidays being created are in range of specified schedule
            boolean isBeforeOrEqual = (existingSchedule.getDateFrom().isEqual(holidayPlan.getDateFrom()) || existingSchedule.getDateFrom().isBefore(holidayPlan.getDateFrom()));
            boolean isAfterOrEqual = (existingSchedule.getDateUntil().isEqual(holidayPlan.getDateUntil()) || existingSchedule.getDateUntil().isAfter(holidayPlan.getDateUntil()));

            //Both dates or one of the dates is valid
            if (isBeforeOrEqual || isAfterOrEqual) {
                //One of the dates is not valid
                if (!(isBeforeOrEqual && isAfterOrEqual)) {
                    //If starting date is valid and ending date exceeds schedule date
                    if (isBeforeOrEqual)  {
                        holidayPlan.setDateUntil(existingSchedule.getDateUntil());
                    }
                    //If ending date is valid and starting date falls behind schedule date
                    if (isAfterOrEqual) {
                        holidayPlan.setDateFrom(existingSchedule.getDateFrom());
                    }
                }
                try {
                    HolidayPlan holidayPlan1 = new HolidayPlan();
                    holidayPlan1.setHolidayName(holidayPlan.getHolidayName());
                    holidayPlan1.setDateFrom(holidayPlan.getDateFrom());
                    holidayPlan1.setDateUntil(holidayPlan.getDateUntil());
                    holidayPlan1.setSchedule(existingSchedule);
                    HolidayPlan save = holidayPlanRepository.save(holidayPlan1);

                    //existingSchedule.addHoliday(holiday1);
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

    public HolidayPlan getHolidayById(Long holidayId) {
        return holidayPlanRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytos atostogos neegzistuoja.", "Holiday", "Does nor exist", holidayId.toString()));
    }

    public HolidayPlan update(Long holidayId, HolidayPlan holiday) {
        HolidayPlan existingHolidayPlan = holidayPlanRepository.findById(holidayId).orElseThrow(() -> new ValidationException("Nurodytos atostogos neegzistuoja.", "Holiday", "Does nor exist", holidayId.toString()));
        existingHolidayPlan.setHolidayName(holiday.getHolidayName());
        existingHolidayPlan.setDateFrom(holiday.getDateFrom());
        existingHolidayPlan.setDateUntil(holiday.getDateUntil());

        return holidayPlanRepository.save(existingHolidayPlan);
    }

    @Transactional
    public Boolean deleteHoliday(Long holidayId) {
        try {
            holidayPlanRepository.deleteById(holidayId);
            return true;
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return false;
        }
    }
}
