package com.equivi.mailsy.web.views;

import com.equivi.mailsy.web.controller.EmailCollectorController;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by tsagita on 23/9/14.
 */
public class CollectorResultExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> emailsResult = (List<String>) model.get(EmailCollectorController.KEY_RESULT_EMAILS);

        HSSFSheet sheet = workbook.createSheet("Emails Result");

        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Email Address");

        int rowNum = 1;
        for (String email : emailsResult) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(email);
        }

        String fileName = generateFileName();

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }

    private String generateFileName() {
        LocalDate now = new LocalDate();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM_dd_yyyy");

        String fileName = String.format("ImportedEmails_%s.xls", now.toString(dtf));

        return fileName;
    }

}
