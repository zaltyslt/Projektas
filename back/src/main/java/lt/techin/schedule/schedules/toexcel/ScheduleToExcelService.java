package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.schedules.planner.PlannerService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduleToExcelService {
    private final PlannerService plannerService;

    private static final String[] days = {
            "Pirmadienis", "Antradienis", "Trečiadienis", "ketvirtadienis",
            "Penktadienis", "Šeštadienis", "Sekmadienis"};

    private static final String[] months = {
            "Sausis", "Vasaris", "Kovas", "Balandis", "Gegužė", "Birželis",
            "Liepa", "Rugpjūtis", "Rugsėjis", "Spalis", "Lapkritis", "Gruodis"};

    public ScheduleToExcelService(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    public void toExcel(Long id) {
        var workDays = plannerService.getWorkDays(id);

    }

    public void drawCalendar() throws Exception {
        Calendar calendar = LocaleUtil.getLocaleCalendar();
        boolean xlsx = true;
//        LocalDate dateNow = LocalDate.now();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//       calendar.set(2023, Calendar.JANUARY, 1);

        try (Workbook wb = new XSSFWorkbook()) {
            //later
            Map<String, CellStyle> styles = createStyles(wb);

            int monthsCount = 2; //kiek menesiu spausdinti
            int monthToDraw = 1; //kuri menesi spausdinti

            for (int month = monthToDraw ; month < monthToDraw + monthsCount; month++) {

                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(2023, month,1);
                int year = calendar.get(Calendar.YEAR);
               // var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                //create a sheet for each month
                Sheet sheet = wb.createSheet(months[calendar.get(Calendar.MONTH)]); //Sheet name

                //turn off gridlines
                sheet.setDisplayGridlines(false);
                sheet.setPrintGridlines(false);
                sheet.setFitToPage(true);
                sheet.setHorizontallyCenter(true);
                PrintSetup printSetup = sheet.getPrintSetup();
                printSetup.setLandscape(true);

                //the header row: centered text in 48pt font
                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(80);
                Cell titleCell = headerRow.createCell(0);
                titleCell.setCellValue(months[calendar.get(Calendar.MONTH)] + " " + year);
                titleCell.setCellStyle(styles.get("title"));
                sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$N$1"));

                //header with month titles
                Row monthRow = sheet.createRow(1);
                for (int i = 0; i < days.length; i++) {
                    //set column widths, the width is measured in units of 1/256th of a character width
                    sheet.setColumnWidth(i * 2, 5 * 256); //the column is 5 characters wide
                    sheet.setColumnWidth(i * 2 + 1, 13 * 256); //the column is 13 characters wide
                    sheet.addMergedRegion(new CellRangeAddress(1, 1, i * 2, i * 2 + 1));
                    Cell monthCell = monthRow.createCell(i * 2);
                    monthCell.setCellValue(days[i]);
                    monthCell.setCellStyle(styles.get("month"));
                }

                int cnt = 1, day = 1;
                int rownum = 2;

                for (int j = 0; j < 6; j++) { //menuo max gali issitempti per 6 savaites/rows
                    Row row = sheet.createRow(rownum++);
                    row.setHeightInPoints(100);

                    for (int i = 0; i < days.length; i++) { // per days array
                        Cell dayCell_1 = row.createCell(i * 2);
                        Cell dayCell_2 = row.createCell(i * 2 + 1);

                        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0
                                ? 7
                                : calendar.get(Calendar.DAY_OF_WEEK) - 1;
                            var aa = calendar.get(Calendar.MONTH);
                        if (cnt >= day_of_week
                                && calendar.get(Calendar.MONTH) == month
                                ){
                            dayCell_1.setCellValue(day);
                            calendar.set(Calendar.DAY_OF_MONTH, ++day);

                            if (i == days.length - 2 || i == days.length - 1) {
                                dayCell_1.setCellStyle(styles.get("weekend_left"));
                                dayCell_2.setCellStyle(styles.get("weekend_right"));
                            } else {
                                dayCell_1.setCellStyle(styles.get("workday_left"));
                                dayCell_2.setCellStyle(styles.get("workday_right"));
                            }
                       }else {
                            dayCell_1.setCellStyle(styles.get("grey_left"));
                            dayCell_2.setCellStyle(styles.get("grey_right"));
                        }

                        cnt++;
                    }
                    if (calendar.get(Calendar.MONTH) > month) break;
                }


            }

            // Write the output to a file
            String file = "calendar.xlsx";

            try (FileOutputStream out = new FileOutputStream(file)) {
                wb.write(out);
            }

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
        titleFont.setFontHeightInPoints((short) 48);
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
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
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
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_right", style);

        return styles;
    }
}
