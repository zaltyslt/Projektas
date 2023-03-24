package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.planner.PlannerService;
import lt.techin.schedule.schedules.planner.WorkDay;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScheduleToExcelService {
    private final PlannerService plannerService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleToExcelService.class);

    private static final String[] days = {
            "Pirmadienis", "Antradienis", "Trečiadienis", "Ketvirtadienis",
            "Penktadienis"
//            , "Šeštadienis", "Sekmadienis"
    };

    private static final String[] months = {
            "Sausis", "Vasaris", "Kovas", "Balandis", "Gegužė", "Birželis",
            "Liepa", "Rugpjūtis", "Rugsėjis", "Spalis", "Lapkritis", "Gruodis"};

    public ScheduleToExcelService(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    public String toExcel(Long id) {

        List<WorkDay> sortedWorkDays = plannerService.getWorkDays(id).stream()
                .sorted(Comparator.comparing(WorkDay::getDate))
                .distinct()
                .toList();

       return drawCalendar(sortedWorkDays);
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

    public static String getGroupTitle(List<WorkDay> workDayList) {
        return workDayList.get(0).getSchedule().getGroups().getName();
    }

    public static String getWeekTitle(LocalDate date) {
        LocalDate monday = date.getDayOfWeek().getValue() == 2  // 1 == Sunday, 2 == Monday
                ? date
                : date.with(java.time.DayOfWeek.MONDAY);
        LocalDate friday = monday.plusDays(4);
        DateTimeFormatter MMdd = DateTimeFormatter.ofPattern("MM.dd");

        return monday.format(MMdd) + "-" + friday.format(MMdd);
    }

    public String drawCalendar(List<WorkDay> workDayList) {
        Calendar calendar = LocaleUtil.getLocaleCalendar();
        boolean xlsx = true;
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        try (Workbook wb = new XSSFWorkbook()) {

            Map<String, CellStyle> styles = createStyles(wb);

            var startDate = workDayList.get(0).getDate();
            calendar.set(startDate.getYear(), startDate.getMonth().getValue(), startDate.getDayOfMonth());

            int year = calendar.get(Calendar.YEAR);
            Sheet sheet = wb.createSheet(getTitleYears(workDayList)); //Sheet name

            //turn off gridlines
            sheet.setDisplayGridlines(false);
            sheet.setPrintGridlines(false);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            //the header row: centered text in 18pt font
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(40);
            Cell titleCell = headerRow.createCell(0);
            titleCell.setCellValue(getGroupTitle(workDayList) + ", " + getTitleYears(workDayList));
            titleCell.setCellStyle(styles.get("title"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$F$1"));

            //header with day titles
            Row monthRow = sheet.createRow(1);
            sheet.setColumnWidth(0, 25 * 256); //the column is 25 characters wide

            Cell monthCell = monthRow.createCell(0);
            monthCell.setCellValue("Data/Savaitės diena");
            monthCell.setCellStyle(styles.get("month"));
            for (int i = 1; i <= days.length; i++) {
                sheet.setColumnWidth(i, 25 * 256); //the column is 13 characters wide
                monthCell = monthRow.createCell(i);
                monthCell.setCellValue(days[i - 1]);
                monthCell.setCellStyle(styles.get("month"));
            }

            int cnt = 1, wdCount = 0;
            int rowNum = 2;
            Map<Long, CellStyle> colors = new HashMap<>();

            while (workDayList.size() > wdCount) {
                Row row1 = sheet.createRow(rowNum++);
                Row row2 = sheet.createRow(rowNum++);
                Row row3 = sheet.createRow(rowNum++);
                Row row4 = sheet.createRow(rowNum++);
                Row row5 = sheet.createRow(rowNum++);

                Cell nullCell = row1.createCell(0);
                nullCell.setCellValue(getWeekTitle(workDayList.get(wdCount).getDate()));
                sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowNum - 4) + ":$A$" + (rowNum)));
                nullCell.setCellStyle(styles.get("workday_left"));

                for (int d = 1; d < 6; d++) {

                    if (wdCount < workDayList.size() && workDayList.get(wdCount).getDate().getDayOfWeek().getValue() == cnt) {
                        row1.createCell(d).setCellStyle(styles.get("common"));
                        row1.getCell(d).setCellValue(workDayList.get(wdCount).getSubject().getName());

                        if(!colors.containsKey(workDayList.get(wdCount).getSubject().getId())){
                            CellStyle subjectCell;
                            byte opacity = (byte) 128;
//                            XSSFColor xssfColor = new XSSFColor(indexedColor, null);
//                            xssfColor.setAlpha(opacity);
                            subjectCell = wb.createCellStyle();
                            subjectCell.setFillForegroundColor((short) (colors.size()+3));
                            subjectCell.setFillPattern(FillPatternType.FINE_DOTS);
                            colors.put(workDayList.get(wdCount).getSubject().getId(), subjectCell);
                        }
                        row1.getCell(d).setCellStyle(colors.get(workDayList.get(wdCount).getSubject().getId()));

                        var current = workDayList.get(wdCount);
                        row2.createCell(d).setCellStyle(styles.get("common"));
                        row2.getCell(d).setCellValue(current.getLessonStart() + " - " + current.getLessonEnd());
                        row3.createCell(d).setCellStyle(styles.get("common"));
                        row3.getCell(d).setCellValue(current.getTeacher().getfName() + " " + current.getTeacher().getlName());
                        row4.createCell(d).setCellStyle(styles.get("common"));
                        row4.getCell(d).setCellValue(current.getOnline()
                                ? "Nuotolinė pamoka"
                                : "Klasė: " + current.getClassroom().getClassroomName());
                        row5.createCell(d).setCellStyle(styles.get("common"));
                        row5.getCell(d).setCellValue(current.getDate().toString());

                        cnt++;
                        wdCount++;
                    } else {
                        row1.createCell(d).setCellStyle(styles.get("grey_right"));
                        row2.createCell(d).setCellStyle(styles.get("grey_right"));
                        row3.createCell(d).setCellStyle(styles.get("grey_right"));
                        row4.createCell(d).setCellStyle(styles.get("grey_right"));
                        row5.createCell(d).setCellStyle(styles.get("grey_right"));

                        cnt++;
                    }
                        if (workDayList.size() == wdCount && (cnt-1) % 5 == 0 ) {break;}
                }

                CellRangeAddress range = CellRangeAddress.valueOf("A"+ (rowNum - 4) + ":F" + (rowNum));

// Apply a border to the range
                RegionUtil.setBorderTop(BorderStyle.MEDIUM, range, sheet);
                RegionUtil.setBorderBottom(BorderStyle.MEDIUM, range, sheet);
                RegionUtil.setBorderLeft(BorderStyle.MEDIUM, range, sheet);
                RegionUtil.setBorderRight(BorderStyle.MEDIUM, range, sheet);
                cnt = 1;
            }

            // Write the output to a file
            File file = new File("example.xlsx");
            int count = 1;
            while (file.exists()) {
                file = new File("example" + count + ".xlsx");
                count++;
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                wb.write(out);
                var aa = file.getPath();
                var bb = file.getName();
                return aa;
            }

        } catch (Exception e) {
            throw new ValidationException("Nepavyko sukurti Excel failo", "", "", "");
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

        Font dayFont = wb.createFont();
        dayFont.setFontHeightInPoints((short) 14);
        dayFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("weekend_left", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("weekend_right", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("workday_left", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("workday_right", style);

        style = wb.createCellStyle();
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_left", style);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_right", style);

        style = wb.createCellStyle();
          style.setIndention((short) 1);
        style.setBorderLeft(BorderStyle.MEDIUM);
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
