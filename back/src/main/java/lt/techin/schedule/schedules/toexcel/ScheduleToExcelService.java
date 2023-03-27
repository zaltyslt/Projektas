package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.holidays.Holiday;
import lt.techin.schedule.schedules.holidays.HolidayService;
import lt.techin.schedule.schedules.planner.PlannerService;
import lt.techin.schedule.schedules.planner.WorkDay;
import lt.techin.schedule.subject.Subject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleToExcelService {
    private final PlannerService plannerService;
    private final HolidayService holidayService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleToExcelService.class);

    private static final String[] days = {
            "Pirmadienis", "Antradienis", "Trečiadienis", "Ketvirtadienis",
            "Penktadienis"
//            , "Šeštadienis", "Sekmadienis"
    };

    private static final String[] months = {
            "Sausis", "Vasaris", "Kovas", "Balandis", "Gegužė", "Birželis",
            "Liepa", "Rugpjūtis", "Rugsėjis", "Spalis", "Lapkritis", "Gruodis"};

    public ScheduleToExcelService(PlannerService plannerService, HolidayService holidayService) {
        this.plannerService = plannerService;
        this.holidayService = holidayService;
    }

    public List<WorkDay> addHolidays(List<WorkDay> workDays, List<Holiday> holidays) {
        List<WorkDay> holidaysToWorkdays = new ArrayList<>();

        for (Holiday holiday : holidays) {
            long daysToDo = ChronoUnit.DAYS.between(holiday.getDateFrom(), holiday.getDateUntil()) + 1;
            LocalDate date = holiday.getDateFrom();
            Subject subject = new Subject();
            subject.setName(holiday.getHolidayName());
            Schedule scheduleName = workDays.get(0).getSchedule();

            for (long i = 0L; i < daysToDo; i++) {
//                var dd = date.plusDays(i).getDayOfWeek().getValue();
                if (date.plusDays(i).getDayOfWeek().getValue() != 6
                        && date.plusDays(i).getDayOfWeek().getValue() != 7) {
                    WorkDay workday = new WorkDay();
                    workday.setDate(date.plusDays(i));
                    workday.setSubject(subject);
                    workday.setSchedule(scheduleName);
                    workday.setOnline(true);
                    holidaysToWorkdays.add(workday);
                }
            }
        }
        workDays.addAll(holidaysToWorkdays);
        List<WorkDay> sortedWorkDays = workDays.stream()
                .sorted(Comparator.comparing(WorkDay::getDate))
                .distinct()
                .toList();
        return sortedWorkDays;
    }

    public List<WorkDay> addEmptyDays(List<WorkDay> sortedDays) {
        var firstDay = sortedDays.get(0).getDate();
        var lastDay = sortedDays.get(sortedDays.size() - 1).getDate();
        LocalDate startOfMonth = firstDay.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastOfMonth = lastDay.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1L);
        LocalDate pointerDate = startOfMonth;

        int index = 0; //atejusiu workDay
        List<WorkDay> calendar = new ArrayList<>();
        while (pointerDate.isBefore(lastOfMonth)) {
            if (index < sortedDays.size() && sortedDays.get(index).getDate().isEqual(pointerDate)) {
                calendar.add(sortedDays.get(index));
                index++;
            } else {
                if (pointerDate.getDayOfWeek().getValue() != 6 && pointerDate.getDayOfWeek().getValue() != 7) {
                    WorkDay newDay = new WorkDay();
                    Subject subject = new Subject();
                    subject.setName("");
                    newDay.setSubject(subject);
                    newDay.setDate(pointerDate);
                    calendar.add(newDay);
                }
            }
            pointerDate = pointerDate.plusDays(1L);
        }
        return calendar;
    }

    public String toExcel(Long id, boolean paged) {
        List<WorkDay> workDays = plannerService.getWorkDays(id);
        List<Holiday> holidays = holidayService.getById(id);

        List<WorkDay> mixedDays = addHolidays(workDays, holidays);
        mixedDays = addEmptyDays(mixedDays);

        if (paged) {
            return drawCalendar(toMonths(mixedDays), paged);
        } else {
            return drawCalendar(toWeeks(mixedDays), paged);
        }
//        return drawCalendar(sortedWorkDays);
//        return null;
    }

    public Map<Integer, List<WorkDay>> toMonths(List<WorkDay> sortedWorkDays) {
        Map<Integer, List<WorkDay>> groupedWorkdays = sortedWorkDays.stream()
                .collect(Collectors
                        .collectingAndThen(
                                Collectors.groupingBy(workDay -> YearMonth.from(workDay.getDate())),
                                map -> map.entrySet().stream()
                                        .collect(Collectors.toMap(
                                                entry -> entry.getKey().getYear() * 12 + entry.getKey().getMonthValue(),
                                                Map.Entry::getValue
                                        ))
                        ));
        return groupedWorkdays;
    }

    public Map<Integer, List<WorkDay>> toWeeks(List<WorkDay> sortedWorkDays) {
//        WeekFields weekFields = WeekFields.of(java.util.Locale.getDefault());
        // Group the objects by week number
//        Map<Integer, List<WorkDay>> groupedWorkdays = sortedWorkDays.stream()
//                .collect(Collectors.groupingBy(
//                        wd -> wd.getDate().get(weekFields.weekOfYear()),
//                        Collectors.toList()));
        Map<Integer, List<WorkDay>> groupedWorkdays = new HashMap<>();
        groupedWorkdays.put(1,sortedWorkDays);
        return groupedWorkdays;
    }

    public static String getTitleYears(List<WorkDay> workDayList) {
        List<Integer> years = workDayList.stream()
                .map(day -> day.getDate().getYear())
                .distinct()
                .toList();

        return years.size() > 1
                ? years.get(0).toString() + "/" + years.get(1).toString()
                : years.get(0).toString();
    }

    public static String getGroupTitle(WorkDay workDay) {
        return workDay.getSchedule().getGroups().getName();
    }

    public static String getWeekTitle(LocalDate date) {
        LocalDate monday = date.getDayOfWeek().getValue() == 2  // 1 == Sunday, 2 == Monday
                ? date
                : date.with(java.time.DayOfWeek.MONDAY);
        LocalDate friday = monday.plusDays(4);
        DateTimeFormatter MMdd = DateTimeFormatter.ofPattern("MM.dd");

        return monday.format(MMdd) + "-" + friday.format(MMdd);
    }

    public void setCellsStyle(Sheet sheet, Map<String, CellStyle> stylesList, String styleName, int column, int startRow, int endRow) {
        CellStyle style = stylesList.get(styleName);
        for (int i = startRow; i <= endRow; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(column);
            cell.setCellStyle(style);
//            cell.setCellValue(i);
//            logger.debug(style.toString());
        }
    }

    public static void createRows(Sheet sheet, int rowsCount) {
        int lastRow = sheet.getLastRowNum();
        for (int i = 1; i <= rowsCount; i++) {
            sheet.createRow(lastRow + i);
        }
    }

    public static void createCells(Sheet sheet, int startRow, int endRow, int cell) {
        for (int i = startRow; i <= endRow; i++) {
            sheet.getRow(i).createCell(cell);
        }
    }

    public static void writeCollors(Sheet sheet) {
        var rowNo = sheet.getLastRowNum();
        sheet.createRow(++rowNo).createCell(0).setCellValue("***");

        var colors = IndexedColors.values();

        for (var color : colors) {
            Row row = sheet.createRow(++rowNo);
            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);

            cell0.setCellValue(color.toString());
            cell1.setCellValue(color.getIndex());

            CellStyle style = sheet.getWorkbook().createCellStyle();
            style.setFillForegroundColor(color.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell0.setCellStyle(style);
            cell1.setCellStyle(style);
        }
    }

    public String drawCalendar(Map<Integer, List<WorkDay>> workDayList, boolean paged) {

//        boolean xlsx = true;
        Calendar calendar = LocaleUtil.getLocaleCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstRow = 0;
        StringBuilder printArea = new StringBuilder();

        try (Workbook wb = new XSSFWorkbook()) {

            Map<String, CellStyle> styles = createStyles(wb);
            Sheet sheet = wb.createSheet("Sheet1"); //Sheet name

            List<Month> monthToPrint = new ArrayList<>();

               for (List<WorkDay> month : workDayList.values()) {
                   Month tempMonth = new Month();
                   tempMonth.setWorkDays(month);
                   monthToPrint.add(tempMonth);
               }

            for (Month month : monthToPrint) {

                var startDate = month.getWorkDays().get(0).getDate();
                calendar.set(startDate.getYear(), startDate.getMonth().getValue(), startDate.getDayOfMonth());

                int year = calendar.get(Calendar.YEAR);


                //turn off gridlines
                sheet.setDisplayGridlines(false);
                sheet.setPrintGridlines(false);
                sheet.setFitToPage(true);
                sheet.setHorizontallyCenter(true);
                PrintSetup printSetup = sheet.getPrintSetup();
                printSetup.setLandscape(true);

                    //the header row: centered text in 18pt font

                    Row headerRow = sheet.createRow(sheet.getLastRowNum()+1);
                    headerRow.setHeightInPoints(40);
                    Cell titleCell = headerRow.createCell(0);

                    titleCell.setCellValue(month.getGroup() + ", " + getTitleYears(month.getWorkDays()));
                    titleCell.setCellStyle(styles.get("title"));
                    sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (sheet.getLastRowNum() + 1) + ":$F$" + (sheet.getLastRowNum() + 1)));

                    //header with day titles
                    Row monthRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    sheet.setColumnWidth(0, 25 * 256); //the column is 25 characters wide

                    Cell monthCell = monthRow.createCell(0);
                    monthCell.setCellValue("Data/Savaitės diena");
                    monthCell.setCellStyle(styles.get("month"));
                    for (int i = 1; i <= days.length; i++) {
                        sheet.setColumnWidth(i, 25 * 256);
                        monthCell = monthRow.createCell(i);
                        monthCell.setCellValue(days[i - 1]);
                        monthCell.setCellStyle(styles.get("month"));
                    }


                int cnt = 1, wdCount = 0;
                int rowNum = 2;
                List<WorkDay> workDays = month.getWorkDays();
                //Map subjectId, Color for subject name
                Map<Long, CellStyle> colors = new HashMap<>();
                while (workDays.size() > wdCount) {

                    createRows(sheet, 4);
                    var actualRow = sheet.getLastRowNum() - 3;
                    createCells(sheet, actualRow, sheet.getLastRowNum(), 0);
                    setCellsStyle(sheet, styles, "week", 0, actualRow, actualRow + 3);

                    Cell nullCell = sheet.getRow(actualRow).getCell(0);
                    nullCell.setCellValue(getWeekTitle(workDays.get(wdCount).getDate()));
                    sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (actualRow + 1) + ":$A$" + (actualRow + 4)));
                    nullCell.setCellStyle(styles.get("week"));

                    for (int d = 1; d < 6; d++) {

                        createCells(sheet, actualRow, actualRow + 3, d);

                        if (wdCount < workDays.size() && workDays.get(wdCount).getDate().getDayOfWeek().getValue() == cnt) {
                            var current = workDays.get(wdCount);
                            var currentDate = current.getDate();
                            var curentDay = currentDate.getDayOfWeek().getValue();
                            sheet.getRow(actualRow).getCell(d).setCellValue(current.getSubject().getName());

                            if (current.getId() != null && current.getOnline() != null) {
                                //aprasyti diena
                                setCellsStyle(sheet, styles, "common", d, actualRow, actualRow + 3);
                                sheet.getRow(actualRow + 1).getCell(d).setCellValue(current.getLessonStart() != null
                                        ? current.getLessonStart() + " - " + current.getLessonEnd()
                                        : "");
                                sheet.getRow(actualRow + 2).getCell(d).setCellValue(current.getTeacher() != null
                                        ? current.getTeacher().getfName() + " " + current.getTeacher().getlName()
                                        : "");
                                if (current.getOnline()) {
                                    sheet.getRow(actualRow + 3).getCell(d).setCellValue("Nuotolinė pamoka");
                                    sheet.getRow(actualRow + 3).getCell(d).setCellStyle(styles.get("remote"));
                                } else {
                                    sheet.getRow(actualRow + 3).getCell(d).setCellValue("Klasė: " + current.getClassroom().getClassroomName());
                                }
//                                sheet.getRow(actualRow + 4).getCell(d).setCellValue(current.getDate().toString());

                                //paint subject name
                                subjectStyling(wb, colors, current);

                                sheet.getRow(actualRow).getCell(d).setCellStyle(colors.get(current.getSubject().getId()));

                            } else if (current.getId() == null && current.getOnline() != null) {
                                // aprasyti atostogas
                                setCellsStyle(sheet, styles, "holiday", d, actualRow, actualRow + 3);
                            }else{
                                setCellsStyle(sheet, styles, "empty", d, actualRow, actualRow + 3);
                            }
                            cnt++;
                            wdCount++;

                        } else {
                            //piesiam tusciadieni
                            setCellsStyle(sheet, styles, "empty", d, actualRow, actualRow + 3);
                            cnt++;
                        }
                        if (workDays.size() == wdCount && (cnt - 1) % 5 == 0) {
                            break;
                        }
                    }

                    CellRangeAddress range = CellRangeAddress.valueOf("A" + (actualRow + 1) + ":F" + (actualRow + 4));

                    //Apply a border to the range
                    RegionUtil.setBorderTop(BorderStyle.MEDIUM, range, sheet);
                    RegionUtil.setBorderBottom(BorderStyle.MEDIUM, range, sheet);
                    RegionUtil.setBorderLeft(BorderStyle.MEDIUM, range, sheet);
                    RegionUtil.setBorderRight(BorderStyle.MEDIUM, range, sheet);
                    cnt = 1;

                }

                printArea.append("$A$").append(firstRow + 1).append(":$F$").append(sheet.getLastRowNum() + 1).append(",");

                sheet.createRow(sheet.getLastRowNum() + 1);
                sheet.setRowBreak(sheet.getLastRowNum());
                firstRow = sheet.getLastRowNum() + 1;

                wb.setPrintArea(0, printArea.toString().substring(0,printArea.length()-1)); // set print area for sheet 0
            }
            writeCollors(sheet);

            // Write the output to a file
            File file = new File("schedule.xlsx");
            int count = 1;
            while (file.exists()) {
                file = new File("schedule" + count + ".xlsx");
                count++;
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
              wb.write(out);
                return file.getPath();
            }

        } catch (Exception e) {
            logger.error("#ME: " + e.toString());
            throw new ValidationException("Nepavyko sukurti Excel failo", "", "", "");
        }
    }

    public void cleaner(){
        File directory = new File(".");
        File[] files = directory.listFiles((dir, name) -> name.matches("schedule.*\\.xlsx"));
        for (File file : files) {
            System.out.println("Deleting file: " + file.getName());
            file.delete();
        }
    }

    private static void subjectStyling(Workbook wb, Map<Long, CellStyle> subjectStyles, WorkDay current) {
        if (!subjectStyles.containsKey(current.getSubject().getId())) {

            short[] colorValues= new short[]{
                    IndexedColors.CORAL.getIndex(),
                    IndexedColors.LIGHT_ORANGE.getIndex(),
                    IndexedColors.LEMON_CHIFFON.getIndex(),
                    IndexedColors.LIGHT_GREEN.getIndex(),
                    IndexedColors.LIGHT_BLUE.getIndex(),
                    IndexedColors.LIGHT_TURQUOISE.getIndex(),
                    IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),
                    IndexedColors.LAVENDER.getIndex(),
                    IndexedColors.ROSE.getIndex()
                    };

//            int index = subjectStyles.values().stream()
//                    .mapToInt(CellStyle::getFillForegroundColor)
//                    .max()
//                    .orElse(colorValues[0]);
            long index = subjectStyles.keySet().stream()
                    .mapToLong(Long::longValue)
                    .max()
                    .orElse(0L);

            if(index+1 == colorValues.length){ index = 0;}

            CellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.fromInt(colorValues[(int) index]).getIndex());
//            style.setFillForegroundColor(IndexedColors.fromInt(colorValues[index]));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setIndention((short) 1);

            subjectStyles.put(current.getSubject().getId(), style);

        }
    }

    /**
     * cell styles used for formatting calendar sheets
     */
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();

        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();


        CellStyle style;

        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short) 12);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        monthFont.setBold(true);

        style = wb.createCellStyle();
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        styles.put("month", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
        styles.put("week", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
        styles.put("holiday", style);

        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setRightBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
        styles.put("empty", style);

        Font remoteFont = wb.createFont();
//        remoteFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setLeftBorderColor(borderColor);
        style.setRightBorderColor(borderColor);
        style.setBottomBorderColor(borderColor);
        style.setIndention((short) 1);
        style.setFont(remoteFont);
        styles.put("remote", style);

//        Font dayFont = wb.createFont();
//        dayFont.setFontHeightInPoints((short) 14);
//        dayFont.setBold(true);
//        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.LEFT);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setLeftBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
//        style.setFont(dayFont);
//        styles.put("weekend_left", style);

//        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setRightBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
//        styles.put("weekend_right", style);

//        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        style.setBorderLeft(BorderStyle.THIN);
////        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
////        style.setBorderBottom(BorderStyle.THIN);
//        style.setLeftBorderColor(borderColor);
//        style.setTopBorderColor(borderColor);
//        style.setRightBorderColor(borderColor);
//        style.setBottomBorderColor(borderColor);
//
//        style.setFillForegroundColor(IndexedColors.CORAL.getIndex()); //was white
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setFont(dayFont);
//        styles.put("workday_left", style);

//        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setRightBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
//        styles.put("workday_right", style);
//
//        style = wb.createCellStyle();
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(borderColor);
//        styles.put("grey_left", style);

//        style = wb.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderLeft(BorderStyle.MEDIUM);
//        style.setLeftBorderColor(borderColor);
//        style.setBorderRight(BorderStyle.MEDIUM);
//        style.setRightBorderColor(borderColor);
//        style.setBorderBottom(BorderStyle.DOTTED);
//        style.setBottomBorderColor(borderColor);
//        styles.put("grey_right", style);
//
        style = wb.createCellStyle();
        style.setIndention((short) 1);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
//        style.setBorderRight(BorderStyle.MEDIUM);
//        style.setRightBorderColor(borderColor);
//        style.setBorderTop(BorderStyle.DOTTED);
//        style.setTopBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBottomBorderColor(borderColor);
        styles.put("common", style);

        return styles;
    }
}

