package XUONG.Utils;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    private static String exportExcel = "src/main/resources/results";
    private static String dataExcel = "src/main/resources/data";

    public static Workbook getFileExcel(String fileName) throws IOException {
        File file = new File(String.format("%s/%s", dataExcel, fileName));
        if (!file.exists()) {
            throw new FileNotFoundException("File không tồn tại: " + file.getAbsolutePath());
        }
        return new XSSFWorkbook(new FileInputStream(file));
    }

    public static XSSFCellStyle styleAll(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        XSSFCellStyle styleAll = workbook.createCellStyle();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);

        styleAll.setFont(font);

        styleAll.setAlignment(HorizontalAlignment.CENTER);
        styleAll.setVerticalAlignment(VerticalAlignment.CENTER);

        styleAll.setBorderTop(BorderStyle.THIN);
        styleAll.setBorderBottom(BorderStyle.THIN);
        styleAll.setBorderLeft(BorderStyle.THIN);
        styleAll.setBorderRight(BorderStyle.THIN);
        return styleAll;
    }

    public static XSSFCellStyle styleHeader(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        XSSFCellStyle style = styleAll(workbook);
        style.setFont(font);

        XSSFColor navyBlue = new XSSFColor(new Color(23, 55, 94), null);
        style.setFillForegroundColor(navyBlue);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    public static XSSFCellStyle styleContent(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());

        XSSFCellStyle style = styleAll(workbook);
        return style;
    }

    public static void writeExcel(Object[][] dataResult, String fileNameTest) throws IOException {
        String[] headers = {"TEST CASE ID", "TEST SCENARIO", "TEST CASE DESCRIPTION", "PRE-CONDITION", "TEST STEPS", "TEST DATA", "EXPECTED RESULT", "ACTUAL RESULT", "STATUS (PASS/FAIL)", "IMAGE ERROR"};
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test Cases");

        XSSFCellStyle headerStyle = styleHeader(workbook);
        XSSFCellStyle contentStyle = styleContent(workbook);

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(30);
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int firstRow = 1;
        List<String> testSteps = new ArrayList<>();
        List<String> testData = new ArrayList<>();
        for(int i = 0; i<dataResult.length; i++){
            testSteps = (List<String>) dataResult[i][4];
            testData = (List<String>) dataResult[i][5];
            int lastRow = firstRow + testSteps.size() - 1;
            for (int j = 0; j < dataResult[i].length; j++) {

                if(headers[j].equalsIgnoreCase("IMAGE ERROR")){
                    for (int r = firstRow; r <= lastRow; r++) {
                        XSSFRow row = sheet.getRow(r);
                        if (row == null) {
                            row = sheet.createRow(r);
                        }
                        XSSFCell cell = row.createCell(j);
                        cell.setCellStyle(contentStyle);
                    }
                    sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, j, j));

                    XSSFRow firstMergedRow = sheet.getRow(firstRow);
                    XSSFCell firstCell = firstMergedRow.getCell(j);
                    if (firstCell == null) {
                        firstCell = firstMergedRow.createCell(j);
                    }

                    if(dataResult[i][j-1].equals("FAIL")){

                        CreationHelper createHelper = workbook.getCreationHelper();
                        Hyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
                        String pathImages = ExcelUtil.class.getClassLoader().getResource("images").getPath();
                        String handleURL = "file:///" + pathImages.substring(1).split("target")[0] + dataResult[i][j].toString();

                        link.setAddress(handleURL);
                        firstCell.setHyperlink(link);

                        firstCell.setCellValue("Xem ảnh Fail");
                        contentStyle.setWrapText(true);
                        firstCell.setCellStyle(contentStyle);
                    } else {
                        firstCell.setCellValue("");
                        contentStyle.setWrapText(true);
                        firstCell.setCellStyle(contentStyle);
                    }
                }
                else if(headers[j].equalsIgnoreCase("TEST STEPS")){
                    for (int k = 0; k < testSteps.size(); k++) {
                        XSSFRow row = sheet.getRow(firstRow + k);
                        row.setHeightInPoints(20);
                        XSSFCell cell = row.createCell(j);
                        if(testSteps.get(k).length() > 0){
                            if (row == null) {
                                row = sheet.createRow(firstRow + k);
                            }
                            cell.setCellValue(testSteps.get(k));
                            contentStyle.setWrapText(true);
                            cell.setCellStyle(contentStyle);
                        }
                        else {
                            cell.setCellValue(testSteps.get(k));
                            contentStyle.setWrapText(true);
                            cell.setCellStyle(contentStyle);
                        }

                    }
                }
                else if(headers[j].equalsIgnoreCase("TEST DATA")){

                    for (int k = 0; k < testSteps.size(); k++) {
                        XSSFRow row = sheet.getRow(firstRow + k);
                        row.setHeightInPoints(20);
                        XSSFCell cell = row.createCell(j);
                        if(k < testData.size()){
                            if (row == null) {
                                row = sheet.createRow(firstRow + k);
                            }
                            cell.setCellValue(testData.get(k));
                            contentStyle.setWrapText(true);
                            cell.setCellStyle(contentStyle);
                        }
                        else {
                            cell.setCellValue("");
                            contentStyle.setWrapText(true);
                            cell.setCellStyle(contentStyle);
                        }
                    }
                }
                else {
                    for (int r = firstRow; r <= lastRow; r++) {
                        XSSFRow row = sheet.getRow(r);
                        if (row == null) {
                            row = sheet.createRow(r);
                        }
                        XSSFCell cell = row.createCell(j);
                        cell.setCellStyle(contentStyle);
                    }
                    sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, j, j));

                    XSSFRow firstMergedRow = sheet.getRow(firstRow);
                    XSSFCell firstCell = firstMergedRow.getCell(j);
                    if (firstCell == null) {
                        firstCell = firstMergedRow.createCell(j);
                    }
                    firstCell.setCellValue(dataResult[i][j].toString());
                    contentStyle.setWrapText(true);
                    firstCell.setCellStyle(contentStyle);

                }
            }
            firstRow += testSteps.size();
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 40 * 256);
        }

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = String.format("Test_%s_", fileNameTest.split(".xlsx")[0]) + timeStamp + ".xlsx";

        File directory = new File(exportExcel);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = exportExcel + "\\" + fileName;
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

    }
}
